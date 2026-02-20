package fr.upec.episen.sirius.fimafeng.implementations;

import fr.upec.episen.sirius.fimafeng.models.Announce;
import fr.upec.episen.sirius.fimafeng.models.User;
import fr.upec.episen.sirius.fimafeng.models.enums.AnnounceStatus;
import fr.upec.episen.sirius.fimafeng.models.enums.AnnounceType;
import fr.upec.episen.sirius.fimafeng.repositories.AnnounceRepository;
import fr.upec.episen.sirius.fimafeng.services.UserService;
import jakarta.transaction.Transactional;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ParquetReaderImplementation {

    private static Logger LOGGER = Logger.getLogger(ParquetReaderImplementation.class.getName());
    private static final int BATCH_SIZE = 1000;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    UserService userService;

    @Autowired
    AnnounceRepository announceRepository;

    @Transactional
    public void importParquet(String filePath) throws IOException {
        LOGGER.info("Importing parquet file " + filePath);

        Path path = new Path(filePath);

        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(path).build()) {

            GenericRecord record;
            List<Announce> announceBatch = new ArrayList<>();

            LOGGER.info("Starting parquet read");
            while ((record = reader.read()) != null) {
                Announce announce = mapperRecordToAnnounce(record);
                announceBatch.add(announce);

                if(announceBatch.size() >= BATCH_SIZE) {
                    LOGGER.info("Saving some announces");
                    announceRepository.saveAll(announceBatch);
                    announceBatch.clear();
                }

            }
            if (!announceBatch.isEmpty()) {
                LOGGER.info("Saving last announces");
                announceRepository.saveAll(announceBatch);
            }
        }
    }

    @Transactional
    public Announce mapperRecordToAnnounce(GenericRecord record) {
        Announce announce = new Announce();
        String userUsername = record.get("author").toString();
        User optionalUser = userService.findByUsername(userUsername);
        // Creating user if not existing
        if(optionalUser == null) {
            User newUser = new User();
            newUser.setUsername(userUsername);
            newUser.setEmail(userUsername.split("@")[0].toLowerCase() + "@email.com");
            newUser = userService.save(newUser);
            announce.setAuthorId(newUser.getId());
        } else {
            announce.setAuthorId(optionalUser.getId());
        }
        announce.setPublicationDate(Date.from(Instant.parse(record.get("publication_date").toString())));
        announce.setStatus(AnnounceStatus.fromValue( (Integer) record.get("status")));
        announce.setType(AnnounceType.fromValue( (Integer) record.get("type")));
        announce.setTitle(record.get("title").toString());
        announce.setDescription(record.get("description").toString());
        try {
            announce.setDateTimeStart(sdf.parse(record.get("date_time_start").toString()));
            announce.setDuration(Float.parseFloat(record.get("duration").toString()));
            announce.setDateTimeEnd(Date.from(Instant.parse(record.get("date_time_end").toString())));
        } catch (ParseException e) {
            LOGGER.warning(e.getMessage());
            throw new RuntimeException(e);
        }
        return announce;
    }





}

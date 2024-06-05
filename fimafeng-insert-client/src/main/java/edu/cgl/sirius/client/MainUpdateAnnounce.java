package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.ConfigLoader;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

public class MainUpdateAnnounce {

    private final static String LoggingLabel = "U p d a t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public MainUpdateAnnounce(String requestOrder, String annonce_id, String status, String title, String description,
            String date_time_start, String duration, String date_time_end, String is_recurrent,
            String slots_number, String price, String ref_location_id,
            ArrayList<String> tags_ids) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        Announce announce = new Announce();
        announce.setAnnounce_id(annonce_id);
        announce.setStatus(status);
        announce.setTitle(title);
        announce.setDescription(description);
        announce.setDate_time_start(date_time_start);
        announce.setDuration(duration);
        announce.setDate_time_end(date_time_end);
        announce.setIs_recurrent(is_recurrent);
        announce.setSlots_number(slots_number);
        announce.setPrice(price);
        announce.setRef_location_id(ref_location_id);
        announce.setAnnounceTags(tags_ids);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedAnnounce = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(announce);
        logger.trace("Annonce with its JSON face : {}", jsonifiedAnnounce);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedAnnounce);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        final UpdateAnnouncesClientRequest clientRequest = new UpdateAnnouncesClientRequest(
                networkConfig,
                birthdate++, request, announce, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            final Announce announceG = (Announce) joinedClientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    joinedClientRequest.getThreadName(),
                    announceG.getAnnounce_id(), announceG.getRef_author_id(), announceG.getPublication_date(),
                    announceG.getStatus(), announceG.getType(), announceG.getTitle(), announceG.getDescription(),
                    announceG.getDate_time_start(), announceG.getDuration(), announceG.getDate_time_end(),
                    announceG.getIs_recurrent(), announceG.getSlots_number(), announceG.getSlots_available(),
                    announceG.getPrice(), announceG.getRef_location_id(),
                    joinedClientRequest.getResult());
        }
    }
}
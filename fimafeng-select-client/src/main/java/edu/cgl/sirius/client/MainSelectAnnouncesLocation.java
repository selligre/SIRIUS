package edu.cgl.sirius.client;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.vandermeer.asciitable.AsciiTable;
import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.ConfigLoader;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.LoggingUtils;
import edu.cgl.sirius.commons.Request;

public class MainSelectAnnouncesLocation {
    private final static String LoggingLabel = "S e l e c t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String requestOrder = "SELECT_ALL_ANNOUNCES";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static Announces announcesLocation;

    public Announces getAnnouncesLocation() {
        return announcesLocation;
    }

    public MainSelectAnnouncesLocation(String requestOrder, String locationId) throws JsonProcessingException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        Announce announceLocationId = new Announce();
        announceLocationId.setRef_location_id(locationId);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectMapper objectMapper2 = new ObjectMapper();
        final String jsonifiedAnnounce = objectMapper2.writerWithDefaultPrettyPrinter()
                .writeValueAsString(announceLocationId);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedAnnounce);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        byte[] requestBytes;
        try {
            requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
            SelectAllAnnouncesLocationClientRequest clientRequest;
            clientRequest = new SelectAllAnnouncesLocationClientRequest(
                    networkConfig,
                    birthdate++, request, announceLocationId, requestBytes);

            clientRequests.push(clientRequest);

            while (!clientRequests.isEmpty()) {
                final ClientRequest joinedClientRequest = clientRequests.pop();
                joinedClientRequest.join();
                logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                announcesLocation = (Announces) joinedClientRequest.getResult();
                final AsciiTable asciiTable = new AsciiTable();
                for (final Announce announce : announcesLocation.getAnnounces()) {
                    asciiTable.addRule();
                    asciiTable.addRow(announce.getAnnounce_id(), announce.getRef_author_id(),
                            announce.getPublication_date(),
                            announce.getStatus(), announce.getType(), announce.getTitle(), announce.getDescription(),
                            announce.getDate_time_start(),
                            announce.getDuration(), announce.getDate_time_end(), announce.getIs_recurrent(),
                            announce.getSlots_number(),
                            announce.getSlots_available(), announce.getPrice(), announce.getRef_location_id());
                }
                asciiTable.addRule();
                // logger.debug("\n{}\n", asciiTable.render());

            }
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

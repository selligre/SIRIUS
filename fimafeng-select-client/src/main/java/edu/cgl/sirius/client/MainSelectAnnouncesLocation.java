package edu.cgl.sirius.client;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

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
    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String requestOrder = "SELECT_ALL_ANNOUNCES";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static Announces announcesLocation;

    public Announces getAnnouncesLocation() {
        return announcesLocation;
    }

    public MainSelectAnnouncesLocation(String requestOrder, String location) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        Announce announceLocationName = new Announce();
        announceLocationName.setRef_location_id(location);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectMapper objectMapper2 = new ObjectMapper();
        final String jsonifiedAnnounce = objectMapper2.writerWithDefaultPrettyPrinter().writeValueAsString(announceLocationName);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedAnnounce);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllAnnouncesLocationClientRequest clientRequest = new SelectAllAnnouncesLocationClientRequest(
                networkConfig,
                birthdate++, request, announceLocationName, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            announcesLocation = (Announces) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final Announce announceLocation : announcesLocation.getAnnounces()) {
                asciiTable.addRule();
                asciiTable.addRow(announceLocation.getAnnounce_id(), announceLocation.getRef_author_id(), announceLocation.getPublication_date(), 
                announceLocation.getStatus(), announceLocation.getType(), announceLocation.getTitle(), announceLocation.getDescription(), announceLocation.getDate_time_start(), 
                announceLocation.getDuration(), announceLocation.getDate_time_end(), announceLocation.getIs_recurrent(), announceLocation.getSlots_number(), announceLocation.getSlots_available(),
                announceLocation.getPrice(), announceLocation.getRef_location_id());
                // sBuilder.append(User.getfirst_name() + "; " + User.getName() + "; " +
                // User.getGroup() + "\n");
            }
            asciiTable.addRule();
            logger.debug("\n{}\n", asciiTable.render());
            // logger.debug("\n{}\n", sBuilder.toString());
        }
    }
}

package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.cgl.sirius.commons.LoggingUtils;
import edu.cgl.sirius.business.dto.UserLocation;
import edu.cgl.sirius.business.dto.UserLocations;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.ConfigLoader;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class MainSelectUsersLocations {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static UserLocations userLocations;

    public UserLocations getUserLocations() {
        return userLocations;
    }

    public MainSelectUsersLocations(String requestOrder) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllUsersLocationsClientRequest clientRequest = new SelectAllUsersLocationsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            userLocations = (UserLocations) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final UserLocation userLocation : userLocations.getUserLocations()) {
                asciiTable.addRule();
                asciiTable.addRow(userLocation.getUser_location_id(), userLocation.getRef_user_id(),
                        userLocation.getRef_location_id());
            }
            asciiTable.addRule();
        }
    }

}

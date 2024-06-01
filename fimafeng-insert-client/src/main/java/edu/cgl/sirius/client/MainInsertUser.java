package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.ConfigLoader;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;

public class MainInsertUser {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public MainInsertUser(String requestOrder, String first_name, String last_name, String display_name, String email,
            String password)
            throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        User user = new User();
        user.setUser_id("DEFAULT");
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setdisplay_name(display_name);
        user.setUser_type("user");
        user.setEmail(email);
        user.setPassword(password);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedAnnounce = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        logger.trace("Student with its JSON face : {}", jsonifiedAnnounce);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedAnnounce);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        final InsertUserClientRequest clientRequest = new InsertUserClientRequest(
                networkConfig,
                birthdate++, request, user, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            final User user2 = (User) joinedClientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    joinedClientRequest.getThreadName(),
                    user2.getUser_id(), user2.getFirst_name(), user2.getLast_name(),
                    user2.getDisplay_name(), user2.getUser_type(), user2.getEmail(), user2.getPassword(),
                    joinedClientRequest.getResult());
        }
    }
}
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
import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.business.dto.Users;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.ConfigLoader;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.LoggingUtils;
import edu.cgl.sirius.commons.Request;

public class MainSelectUsersEmails {
    private final static String LoggingLabel = "S e l e c t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String requestOrder = "SELECT_ALL_USERS_EMAILS";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static Users users;

    public Users getUsers() {
        return users;
    }

    public MainSelectUsersEmails(String requestOrder) throws IOException, InterruptedException {
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
        final SelectAllUsersEmailsClientRequest clientRequest = new SelectAllUsersEmailsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            users = (Users) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final User user : users.getUsers()) {
                asciiTable.addRule();
                asciiTable.addRow(
                        user.getEmail());
            }
            asciiTable.addRule();
            // logger.debug("\n{}\n", asciiTable.render());
        }
    }
}

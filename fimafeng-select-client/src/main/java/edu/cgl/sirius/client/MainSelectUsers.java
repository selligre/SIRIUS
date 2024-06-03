package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.cgl.sirius.commons.LoggingUtils;
import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.business.dto.Users;
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

public class MainSelectUsers {
    private final static String LoggingLabel = "S e l e c t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String requestOrder = "SELECT_ALL_USERS";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static Users users;

    public Users getUsers() {
        return users;
    }

    public MainSelectUsers(String requestOrder) throws IOException, InterruptedException {
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
        final SelectAllUsersClientRequest clientRequest = new SelectAllUsersClientRequest(
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
                asciiTable.addRow(user.getUser_id(), user.getUser_id(), user.getFirst_name(), user.getLast_name(),
                        user.getDisplay_name(), user.getUser_type(), user.getEmail(), user.getPassword());
            }
            asciiTable.addRule();
            // logger.debug("\n{}\n", asciiTable.render());
            // logger.debug("\n{}\n", sBuilder.toString());
        }
    }

    public MainSelectUsers(String requestOrder, String mail, String pswd) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        User uLogin = new User();
        uLogin.setEmail(mail);
        uLogin.setPassword(pswd);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedUser = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(uLogin);

        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedUser);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllUsersClientRequest clientRequest = new SelectAllUsersClientRequest(
                networkConfig,
                birthdate++, request, uLogin, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            users = (Users) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final User user : users.getUsers()) {
                asciiTable.addRule();
                asciiTable.addRow(user.getUser_id(), user.getUser_id(), user.getFirst_name(), user.getLast_name(),
                        user.getDisplay_name(), user.getUser_type(), user.getEmail(), user.getPassword());
            }
            asciiTable.addRule();
            // logger.debug("\n{}\n", asciiTable.render());
            // logger.debug("\n{}\n", sBuilder.toString());
        }
    }

}

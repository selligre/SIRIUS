package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.cgl.sirius.commons.LoggingUtils;
import edu.cgl.sirius.business.dto.Student;
import edu.cgl.sirius.business.dto.Students;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.ConfigLoader;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class MainSelectClient {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String requestOrder = "SELECT_ALL_STUDENTS";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static Students students;

    public Students getStudents() {
        return students;
    }

    // public static void main(String[] args) throws IOException, InterruptedException, SQLException {

    //     final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    //     logger.debug("Load Network config file : {}", networkConfig.toString());

    //     int birthdate = 0;
    //     final ObjectMapper objectMapper = new ObjectMapper();
    //     final String requestId = UUID.randomUUID().toString();
    //     final Request request = new Request();
    //     request.setRequestId(requestId);
    //     request.setRequestOrder(requestOrder);
    //     objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    //     final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
    //     LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
    //     final SelectAllStudentsClientRequest clientRequest = new SelectAllStudentsClientRequest(
    //             networkConfig,
    //             birthdate++, request, null, requestBytes);
    //     clientRequests.push(clientRequest);

    //     while (!clientRequests.isEmpty()) {
    //         final ClientRequest joinedClientRequest = clientRequests.pop();
    //         joinedClientRequest.join();
    //         logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
    //         students = (Students) joinedClientRequest.getResult();
    //         final AsciiTable asciiTable = new AsciiTable();
    //         for (final Student student : students.getStudents()) {
    //             asciiTable.addRule();
    //             asciiTable.addRow(student.getFirstname(), student.getName(), student.getGroup());
    //         }
    //         asciiTable.addRule();
    //         logger.debug("\n{}\n", asciiTable.render());
    //     }
    // }

    public MainSelectClient(String requestOrder) throws IOException, InterruptedException {
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
        final SelectAllStudentsClientRequest clientRequest = new SelectAllStudentsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            students = (Students) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final Student student : students.getStudents()) {
                asciiTable.addRule();
                asciiTable.addRow(student.getFirstname(), student.getName(), student.getGroup());
            }
            asciiTable.addRule();
            logger.debug("\n{}\n", asciiTable.render());
        }
    }

    

}

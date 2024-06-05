package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.cgl.sirius.commons.LoggingUtils;
import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.NumberCount;
import edu.cgl.sirius.business.dto.NumberCounts;
import edu.cgl.sirius.business.dto.Tag;
import edu.cgl.sirius.business.dto.Tags;
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

public class MainSelectNumberCount {
    private final static String LoggingLabel = "S e l e c t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String requestOrder = "SELECT_ALL_TAGS";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static NumberCounts numberCounts;
    private static String number;

    public NumberCounts getNumberCounts() {
        return numberCounts;
    }

    public String getNumber() {
        return number;
    }

    public MainSelectNumberCount(String requestOrder) throws IOException, InterruptedException {
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
        final SelectAllNumberCountClientRequest clientRequest = new SelectAllNumberCountClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);
        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            numberCounts = (NumberCounts) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final NumberCount numberCount : numberCounts.getNumberCounts()) {
                asciiTable.addRule();
                asciiTable.addRow(numberCount.getCount());
                // sBuilder.append(Location.getfirst_name() + "; " + Location.getName() + "; " +
                // Location.getGroup() + "\n");
            }
            asciiTable.addRule();
            // logger.debug("\n{}\n", asciiTable.render());
            // logger.debug("\n{}\n", sBuilder.toString());
        }
    }

    public MainSelectNumberCount(String requestOrder, String announceId) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        Announce annId = new Announce();
        annId.setAnnounce_id(announceId);

        final ObjectMapper objectMapper2 = new ObjectMapper();
        final String jsonifiedAnnounce = objectMapper2.writerWithDefaultPrettyPrinter()
                .writeValueAsString(annId);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedAnnounce);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllNumberCountClientRequest clientRequest = new SelectAllNumberCountClientRequest(
                networkConfig,
                birthdate++, request, annId, requestBytes);
        clientRequests.push(clientRequest);
    }
}

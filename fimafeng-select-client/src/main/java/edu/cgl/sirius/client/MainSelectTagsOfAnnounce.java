package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.cgl.sirius.commons.LoggingUtils;
import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.AnnounceTag;
import edu.cgl.sirius.business.dto.AnnounceTags;
import edu.cgl.sirius.business.dto.Location;
import edu.cgl.sirius.business.dto.Locations;
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

public class MainSelectTagsOfAnnounce {
    private final static String LoggingLabel = "S e l e c t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String requestOrder = "SELECT_TAGS_OF_ANNOUNCE";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static AnnounceTags announceTags;

    public AnnounceTags getAnnounceTags() {
        return announceTags;
    }

    public MainSelectTagsOfAnnounce(String requestOrder, String ref_announce_id)
            throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        Announce announceId = new Announce();
        announceId.setAnnounce_id(ref_announce_id);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectMapper objectMapper2 = new ObjectMapper();
        final String jsonifiedAnnounce = objectMapper2.writerWithDefaultPrettyPrinter()
                .writeValueAsString(announceId);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedAnnounce);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllTagsOfAnnounceClientRequest clientRequest = new SelectAllTagsOfAnnounceClientRequest(
                networkConfig,
                birthdate++, request, announceId, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            announceTags = (AnnounceTags) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final AnnounceTag tag : announceTags.getAnnounces()) {
                asciiTable.addRule();
                asciiTable.addRow(tag.getAnnounce_tag_id(), tag.getRef_announce_id(), tag.getRef_tag_id());
                // sBuilder.append(Location.getfirst_name() + "; " + Location.getName() + "; " +
                // Location.getGroup() + "\n");
            }
            asciiTable.addRule();
            // logger.debug("\n{}\n", asciiTable.render());
            // logger.debug("\n{}\n", sBuilder.toString());
        }
    }

}

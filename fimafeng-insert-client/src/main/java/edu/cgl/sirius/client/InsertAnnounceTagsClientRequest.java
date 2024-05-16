package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.AnnounceTags;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertAnnounceTagsClientRequest extends ClientRequest<AnnounceTags, String> {

    public InsertAnnounceTagsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, AnnounceTags info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> announceTagIdMap = mapper.readValue(body, Map.class);
        final String result = announceTagIdMap.get("announce_tag_id").toString();
        return result;
    }

}

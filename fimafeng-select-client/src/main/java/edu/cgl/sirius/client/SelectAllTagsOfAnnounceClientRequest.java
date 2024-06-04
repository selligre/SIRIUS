package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.AnnounceTags;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllTagsOfAnnounceClientRequest extends ClientRequest<Object, AnnounceTags> {

    public SelectAllTagsOfAnnounceClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Announce info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public AnnounceTags readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final AnnounceTags AnnounceTags = mapper.readValue(body, AnnounceTags.class);
        return AnnounceTags;
    }
}

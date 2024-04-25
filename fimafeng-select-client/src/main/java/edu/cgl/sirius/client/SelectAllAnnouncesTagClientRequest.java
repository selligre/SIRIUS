package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.AnnounceTag;
import edu.cgl.sirius.business.dto.AnnouncesTag;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllAnnouncesTagClientRequest extends ClientRequest<Object, AnnouncesTag> {

    public SelectAllAnnouncesTagClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, AnnounceTag info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public AnnouncesTag readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final AnnouncesTag AnnouncesTag = mapper.readValue(body, AnnouncesTag.class);
        return AnnouncesTag;
    }
}

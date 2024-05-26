package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.AnnounceTag;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllAnnouncesTagClientRequest extends ClientRequest<Object, Announces> {

    public SelectAllAnnouncesTagClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Announce info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Announces readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Announces AnnouncesLocation = mapper.readValue(body, Announces.class);
        return AnnouncesLocation;
    }
}

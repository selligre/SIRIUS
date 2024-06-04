package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Tag;
import edu.cgl.sirius.business.dto.Tags;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllTagsClientRequest extends ClientRequest<Object, Tags> {

    public SelectAllTagsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Tag info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Tags readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Tags AnnouncesLocation = mapper.readValue(body, Tags.class);
        return AnnouncesLocation;
    }
}

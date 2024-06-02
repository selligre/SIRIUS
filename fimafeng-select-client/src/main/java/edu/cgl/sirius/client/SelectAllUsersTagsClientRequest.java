package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.UserTags;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllUsersTagsClientRequest extends ClientRequest<Object, UserTags> {

    public SelectAllUsersTagsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public UserTags readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final UserTags users = mapper.readValue(body, UserTags.class);
        return users;
    }
}

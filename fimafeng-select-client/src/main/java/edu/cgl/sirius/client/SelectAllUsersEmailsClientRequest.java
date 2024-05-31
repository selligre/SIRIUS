package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Users;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllUsersEmailsClientRequest extends ClientRequest<Object, Users> {

    public SelectAllUsersEmailsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Users readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Users users = mapper.readValue(body, Users.class);
        return users;
    }
}

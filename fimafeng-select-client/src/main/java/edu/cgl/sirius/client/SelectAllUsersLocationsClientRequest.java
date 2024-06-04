package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.UserLocations;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllUsersLocationsClientRequest extends ClientRequest<Object, UserLocations> {

    public SelectAllUsersLocationsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public UserLocations readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final UserLocations users = mapper.readValue(body, UserLocations.class);
        return users;
    }
}

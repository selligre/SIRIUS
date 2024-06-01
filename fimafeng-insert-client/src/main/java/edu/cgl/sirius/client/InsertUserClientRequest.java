package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertUserClientRequest extends ClientRequest<User, String> {

    public InsertUserClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, User info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, String> announceIdMap = mapper.readValue(body, Map.class);
        final String result = announceIdMap.get("user_id").toString();
        return result;
    }
}

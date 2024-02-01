package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cgl.sirius.business.dto.Students;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

import java.io.IOException;

public class SelectAllStudentsClientRequest extends ClientRequest<Object, Students> {

    public SelectAllStudentsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Students readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Students students = mapper.readValue(body, Students.class);
        return students;
    }
}

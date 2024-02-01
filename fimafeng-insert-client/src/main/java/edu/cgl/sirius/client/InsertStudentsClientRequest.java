package edu.cgl.sirius.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cgl.sirius.business.dto.Student;
import edu.cgl.sirius.business.dto.Students;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertStudentsClientRequest extends ClientRequest<Student, String> {

    public InsertStudentsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Student info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> studentIdMap = mapper.readValue(body, Map.class);
        final String result  = studentIdMap.get("student_id").toString();
        return result;
    }
}

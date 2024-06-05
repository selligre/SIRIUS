package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.NumberCount;
import edu.cgl.sirius.business.dto.NumberCounts;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllNumberCountClientRequest extends ClientRequest<Object, NumberCounts> {

    public SelectAllNumberCountClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public NumberCounts readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final NumberCounts numberCount = mapper.readValue(body, NumberCounts.class);
        return numberCount;
    }
}

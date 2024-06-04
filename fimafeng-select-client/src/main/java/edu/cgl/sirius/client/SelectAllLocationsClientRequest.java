package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Location;
import edu.cgl.sirius.business.dto.Locations;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllLocationsClientRequest extends ClientRequest<Object, Locations> {

    public SelectAllLocationsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Location info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Locations readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Locations AnnouncesLocation = mapper.readValue(body, Locations.class);
        return AnnouncesLocation;
    }
}

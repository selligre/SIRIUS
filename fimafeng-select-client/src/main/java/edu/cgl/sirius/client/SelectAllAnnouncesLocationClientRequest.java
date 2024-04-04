package edu.cgl.sirius.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.AnnounceLocation;
import edu.cgl.sirius.business.dto.AnnouncesLocation;
import edu.cgl.sirius.client.commons.ClientRequest;
import edu.cgl.sirius.client.commons.NetworkConfig;
import edu.cgl.sirius.commons.Request;

public class SelectAllAnnouncesLocationClientRequest extends ClientRequest<Object, AnnouncesLocation> {

    public SelectAllAnnouncesLocationClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, AnnounceLocation info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public AnnouncesLocation readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final AnnouncesLocation AnnouncesLocation = mapper.readValue(body, AnnouncesLocation.class);
        return AnnouncesLocation;
    }
}

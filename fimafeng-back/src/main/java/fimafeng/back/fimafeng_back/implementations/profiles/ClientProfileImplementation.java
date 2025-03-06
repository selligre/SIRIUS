package fimafeng.back.fimafeng_back.implementations.profiles;

import fimafeng.back.fimafeng_back.models.Client;
import fimafeng.back.fimafeng_back.models.ClientTag;
import fimafeng.back.fimafeng_back.models.Consultation;
import fimafeng.back.fimafeng_back.services.ClientService;
import fimafeng.back.fimafeng_back.services.ClientTagService;
import fimafeng.back.fimafeng_back.services.ConsultationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ClientProfileImplementation extends ClientService {

    private final Logger LOGGER = Logger.getLogger(ClientProfileImplementation.class.getName());

    private final ClientService clientService;
    private final ClientTagService clientTagService;
    private final ConsultationService consultationService;

    public ClientProfileImplementation(ClientService clientService, ClientTagService clientTagService, ConsultationService consultationService) {
        this.clientService = clientService;
        this.clientTagService = clientTagService;
        this.consultationService = consultationService;
    }

    public List<ClientProfile> buildClientProfile() {
        // Retrieve client tags
        ArrayList<ClientTag> clientTags = new ArrayList<>(clientTagService.findAll());
        // Retrieve consultations
        ArrayList<Consultation> consultations = new ArrayList<>(consultationService.findAll());
        // Create profile structure
        ArrayList<ClientProfile> clientProfiles = new ArrayList<>();
        // Retrieve data for 10 users
        int toBeDisplayed = 5;
        while (toBeDisplayed > 0) {
            // Get a random client
            int numberOfClients = clientService.findAll().size();
            int clientId = new Random().nextInt(1, numberOfClients);
            Client client = clientService.findById(clientId);
            // Break if null
            if (client == null) continue;
            // Get client districtId
            int districtId = client.getDistrict();
            // Get client tags
            List<Integer> tagIds = new ArrayList<>();
            for (ClientTag tag : clientTags) {
                if (tag.getRefClientId() == clientId) tagIds.add(tag.getRefTagId());
            }
            // Get client consultations
            List<Integer> consultationIds = new ArrayList<>();
            for (Consultation consultation : consultations) {
                if (consultation.getRefUserId() == clientId) consultationIds.add(consultation.getRefAnnounceId());
            }
            ClientProfile profile = new ClientProfile(clientId, districtId, tagIds, consultationIds);
            clientProfiles.add(profile);
            toBeDisplayed--;
        }
        return clientProfiles;
    }

}

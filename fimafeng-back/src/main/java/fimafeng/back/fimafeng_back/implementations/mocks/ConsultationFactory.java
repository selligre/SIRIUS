package fimafeng.back.fimafeng_back.implementations.mocks;

import fimafeng.back.fimafeng_back.models.*;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.AnnounceTagService;
import fimafeng.back.fimafeng_back.services.ClientService;
import fimafeng.back.fimafeng_back.services.ClientTagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class ConsultationFactory extends Consultation {

    private final ClientService clientService;
    private final AnnounceService announceService;
    private final ClientTagService clientTagService;
    private final AnnounceTagService announceTagService;
    Logger LOGGER = Logger.getLogger(ConsultationFactory.class.getName());

    public ConsultationFactory(ClientService clientService, ClientTagService clientTagService, AnnounceService announceService, AnnounceTagService announceTagService) {
        LOGGER.info("started consultationFactory");
        this.clientService = clientService;
        this.announceService = announceService;
        this.clientTagService = clientTagService;
        this.announceTagService = announceTagService;
    }

    public Consultation generateConsultation() {
        Consultation consultation = new Consultation();
        // retrieve and assign a random Client
        Random random = new Random();
        int numberOfClients = clientService.findAll().size();
        int clientId = random.nextInt(1, numberOfClients);
        Client client = clientService.findById(clientId);
        consultation.setRefUserId(clientId);
        // retrieve and shuffle all announces to make the selection random
        List<Announce> announces = announceService.findAll();
        Collections.shuffle(announces);
        // choose a random way to generate a consultation
        switch (random.nextInt(1, 4)) {
            case 1:
                // if client and announce share the same district
                LOGGER.info("generating shared_district consultation");
                for (Announce announce : announces) {
                    if (announce.getRefDistrictId() == client.getDistrict()) {
                        consultation.setRefAnnounceId(announce.getId());
                        break;
                    }
                }
                break;
            case 2:
                // if client and announce share a tag
                LOGGER.info("generating shared_tag consultation");
                // retrieve client tags
                List<ClientTag> allClientTags = clientTagService.findAll();
                List<Integer> clientTagIds = new ArrayList<>();
                for (ClientTag clientTag : allClientTags) {
                    if (clientTag.getRefClientId() == clientId)
                        clientTagIds.add(clientTag.getRefTagId());
                }
                // retrieve announce tags
                List<AnnounceTag> allAnnounceTags = announceTagService.findAll();
                for (Announce announce : announces) {
                    List<Integer> announceTagIds = new ArrayList<>();
                    for (AnnounceTag announceTag : allAnnounceTags) {
                        if (announceTag.getRefAnnounceId() == announce.getId()) {
                            announceTagIds.add(announceTag.getId());
                        }
                    }
                    // compare announce tags with client tags
                    outerLoop:
                    for (int clientTagId : clientTagIds) {
                        for (int announceTagId : announceTagIds) {
                            if (clientTagId == announceTagId) {
                                consultation.setRefAnnounceId(announce.getId());
                                // break out of both loops using labeled loop
                                // https://stackoverflow.com/questions/886955/how-do-i-break-out-of-nested-loops-in-java
                                break outerLoop;
                            }
                        }
                    }
                }
                break;
            case 3:
                // random announce
                LOGGER.info("generating random consultation");
                consultation.setRefAnnounceId(announces.get(0).getId());
                break;
        }
        LOGGER.info("generated: " + consultation);
        return consultation;
    }

}

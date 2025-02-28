package fimafeng.back.fimafeng_back.implementations.recommendations;

import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.ClientTag;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.ClientTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RecommendationImplementationPOC {
    Logger LOGGER = Logger.getLogger(RecommendationImplementationPOC.class.getName());
    private final ClientTagService clientTagService;

    public RecommendationImplementationPOC(ClientTagService clientTagService, AnnounceService announceService) {
        LOGGER.info("started RecommendationImplementationPOC");
        this.clientTagService = clientTagService;
        // TODO: Setup PostMan request
        // Think about what path to use for the request
        // Think about what parameters are needed
    }

    public List<Announce> generateRecommendations(int clientId) {
        // 1. Get client tags from clientId
        List<Integer> clientTags = new ArrayList<>();
        for (ClientTag tag : clientTagService.findAll()) {
            if (tag.getRefClientId() == clientId)
                clientTags.add(tag.getRefTagId());
        }
        // 2. Get tags popularity from tags
        
        // For each Announce
        // 3. Get announce number of consultations
        // 4. Calculate announce score
        // Return 10 best scores
        return null;
    }
}

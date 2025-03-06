package fimafeng.back.fimafeng_back.implementations.profiles;

import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.AnnounceTag;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.AnnounceTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class AnnounceProfileImplementation extends AnnounceService {

    private final Logger LOGGER = Logger.getLogger(AnnounceProfileImplementation.class.getName());

    private final AnnounceService announceService;
    private final AnnounceTagService announceTagService;

    public AnnounceProfileImplementation(AnnounceService announceService, AnnounceTagService announceTagService) {
        this.announceService = announceService;
        this.announceTagService = announceTagService;
    }

    public List<AnnounceProfile> getAnnouncesData() {
        // Retrieve announces tags
        ArrayList<AnnounceTag> announceTags = new ArrayList<>(announceTagService.findAll());
        // Create data structure [announce_id, district_id, tag1_id, (tag2_id)]
        ArrayList<AnnounceProfile> announceProfiles = new ArrayList<>();
        // Retrieve data for the first 10 announces
        int toBeDisplayed = 5;
        while (toBeDisplayed > 0) {
            // Get a random announce
            int numberOfAnnounces = announceService.findAll().size();
            int announceId = new Random().nextInt(1, numberOfAnnounces);
            Announce announce = announceService.findById(announceId);
            // Break if null
            if (announce == null) continue;
            // Get announce districtId
            int districtId = announce.getRefDistrictId();
            // Get announce tags
            List<Integer> tagIds = new ArrayList<>();
            for (AnnounceTag announceTag : announceTags) {
                if (announceTag.getRefAnnounceId() == announce.getId()) {
                    tagIds.add(announceTag.getRefTagId());
                }
            }
            AnnounceProfile announceProfile = new AnnounceProfile(announceId, districtId, tagIds);
            announceProfiles.add(announceProfile);
            toBeDisplayed--;
        }
        return announceProfiles;
    }
}

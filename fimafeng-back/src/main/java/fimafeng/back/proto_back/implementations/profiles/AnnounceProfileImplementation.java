package fimafeng.back.proto_back.implementations.profiles;

import fimafeng.back.proto_back.models.Announce;
import fimafeng.back.proto_back.models.AnnounceTag;
import fimafeng.back.proto_back.services.AnnounceService;
import fimafeng.back.proto_back.services.AnnounceTagService;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AnnounceProfileImplementation extends AnnounceService {

    private final Logger LOGGER = Logger.getLogger(AnnounceProfileImplementation.class.getName());

    private final AnnounceService announceService;
    private final AnnounceTagService announceTagService;

    public AnnounceProfileImplementation(AnnounceService announceService, AnnounceTagService announceTagService) {
        this.announceService = announceService;
        this.announceTagService = announceTagService;
    }

    public String getAnnouncesData() {
        // Retrieve announces' tags
        ArrayList<AnnounceTag> announceTags = new ArrayList<>(announceTagService.findAll());
        // Create data structure [announce_id, district_id, tag1_id, (tag2_id)]
        ArrayList<Object> announceProfiles = new ArrayList<>();
        // Retrieve data for the first 10 announces
        for (int i = 1; i < 11; i++) {
            if (announceService.findById(i) == null) break;
            ArrayList<Integer> announceProfile = new ArrayList<>();
            Announce announce = announceService.findById(i);
            // Retrieve and store announce id
            announceProfile.add(announce.getId());
            // Retrieve and store announce district id
            announceProfile.add(announce.getRefDistrictId());
            // Retrieve and store announce tags
            for (AnnounceTag announceTag : announceTags) {
                if (announceTag.getRefAnnounceId() == announce.getId()) {
                    announceProfile.add(announceTag.getRefTagId());
                }
            }
            announceProfiles.add(announceProfile);
        }
        // Display the first 10 announces profiles in logs
        // LOGGER.info("announceProfiles: " + announceProfiles);
        return announceProfiles.toString();
    }
}

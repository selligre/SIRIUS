package fimafeng.back.proto_back.services;

import fimafeng.back.proto_back.models.AnnounceTag;
import fimafeng.back.proto_back.repositories.AnnounceTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnounceTagService {
    @Autowired
    private AnnounceTagRepository announceTagRepository;

    public AnnounceTag save(AnnounceTag announceTag) {
        return announceTagRepository.save(announceTag);
    }


    public List<AnnounceTag> saveAll(List<AnnounceTag> announceTags) {
        return announceTagRepository.saveAll(announceTags);
    }


    public AnnounceTag findById(int idAnnounceTag) {
        Optional<AnnounceTag> optionalAnnounceTag = announceTagRepository.findById(idAnnounceTag);
        return optionalAnnounceTag.orElse(null);
    }

    public List<AnnounceTag> findAll() {
        return announceTagRepository.findAll();
    }

    public boolean update(AnnounceTag updatedAnnounceTag) {
        if (updatedAnnounceTag == null) throw new IllegalArgumentException("updatedAnnounceTag is null");
        int id = updatedAnnounceTag.getId();

        Optional<AnnounceTag> optionalAnnounceTag = announceTagRepository.findById(id);
        if (optionalAnnounceTag.isEmpty()) return false;

        AnnounceTag announceTag = optionalAnnounceTag.get();
        announceTag.setRefAnnounceId(updatedAnnounceTag.getRefAnnounceId());
        announceTag.setRefTagId(updatedAnnounceTag.getRefTagId());

        announceTagRepository.saveAndFlush(announceTag);
        return true;
    }

    public boolean delete(int idAnnounceTag) {
        Optional<AnnounceTag> optionalAnnounceTag = announceTagRepository.findById(idAnnounceTag);
        if (optionalAnnounceTag.isPresent()) {
            announceTagRepository.deleteById(idAnnounceTag);
            return true;
        }
        return false;
    }
}

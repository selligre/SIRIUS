package fr.upec.episen.sirius.fimafeng.services;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import fr.upec.episen.sirius.fimafeng.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceBatchService {

    @Autowired
    AnnounceRepository announceRepository;

    public void saveBatch(List<Announce> announces) {
        announceRepository.saveAllAndFlush(announces);
    }
}

package fr.upec.episen.sirius.fimafeng.services;

import fr.upec.episen.sirius.fimafeng.models.Announce;
import fr.upec.episen.sirius.fimafeng.repositories.AnnounceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceBatchService {

    @Autowired
    AnnounceRepository announceRepository;

    @Transactional
    public void saveBatch(List<Announce> announces) {
        announceRepository.saveAllAndFlush(announces);
    }
}

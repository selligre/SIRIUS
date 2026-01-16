package fr.upec.episen.sirius.fimafeng.search.controller;

import fr.upec.episen.sirius.fimafeng.search.model.Announce;
import fr.upec.episen.sirius.fimafeng.search.repository.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
// Important pour le dev local (React port 3000 -> Java port 8080)
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private AnnounceRepository announceRepository;

    @GetMapping
    public List<Announce> search(@RequestParam(required = false) String query) {
        if (query == null || query.isEmpty()) {
            return announceRepository.findAll();
        }
        return announceRepository.findByTitleContainingIgnoreCase(query);
    }
}
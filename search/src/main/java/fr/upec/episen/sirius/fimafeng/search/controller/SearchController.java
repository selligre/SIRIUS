package fr.upec.episen.sirius.fimafeng.search.controller;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import fr.upec.episen.sirius.fimafeng.search.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/search")
// Important pour le dev local (React port 3000 -> Java port 8080)
@CrossOrigin(origins = "*")
public class SearchController {

    private static Logger LOGGER = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private AnnounceRepository announceRepository;

    @GetMapping("/query")
    public Page<Announce> search(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int amount) {
        LOGGER.info("Received GET /query/"+keyword);
        return announceRepository.searchByKeyword(keyword, PageRequest.of(page, amount));
    }
}
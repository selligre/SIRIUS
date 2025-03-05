package fimafeng.back.fimafeng_back.services;

import fimafeng.back.fimafeng_back.models.Tag;
import fimafeng.back.fimafeng_back.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag findById(int idTag) {
        Optional<Tag> optionalTag = tagRepository.findById(idTag);
        return optionalTag.orElse(null);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}

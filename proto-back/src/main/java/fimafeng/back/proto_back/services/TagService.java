package fimafeng.back.proto_back.services;

import fimafeng.back.proto_back.models.Tag;
import fimafeng.back.proto_back.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return tagRepository.findAll();
    }
}

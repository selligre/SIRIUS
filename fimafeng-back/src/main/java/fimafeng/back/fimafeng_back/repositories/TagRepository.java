package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAll(Sort sort);
}

package ua.opnu.labwork2.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.tag.model.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByName(String name);

    Optional<Tag> findByName(String name);
}
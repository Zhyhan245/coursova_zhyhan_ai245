package ua.opnu.labwork2.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.project.model.Project;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);

    List<Project> findAllByNameContainingIgnoreCase(String name);

    List<Project> findByCreatedDateBetween(LocalDate start, LocalDate end);
}
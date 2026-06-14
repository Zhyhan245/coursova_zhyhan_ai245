package ua.opnu.labwork2.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.project.dto.ProjectCreateRequest;
import ua.opnu.labwork2.project.dto.ProjectResponse;
import ua.opnu.labwork2.project.dto.ProjectUpdateRequest;
import ua.opnu.labwork2.project.model.Project;
import ua.opnu.labwork2.project.repository.ProjectRepository;
import ua.opnu.labwork2.task.dto.TaskResponse;
import ua.opnu.labwork2.task.model.Task;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponse create(ProjectCreateRequest request) {

        if (projectRepository.existsByName(request.name())) {
            throw new ConflictOperationException("Project with this name already exists");
        }

        Project project = new Project();
        project.setName(request.name());
        project.setDescription(request.description());
        project.setCreatedDate(LocalDate.now());

        return mapToResponse(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> findAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        return mapToResponse(project);
    }

    @Transactional
    public ProjectResponse update(Long id, ProjectUpdateRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        project.setName(request.name());
        project.setDescription(request.description());

        return mapToResponse(project);
    }

    @Transactional
    public void delete(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        if (project.getTasks() != null && !project.getTasks().isEmpty()) {
            throw new ConflictOperationException("Cannot delete project with existing tasks");
        }

        projectRepository.delete(project);
    }


    @Transactional(readOnly = true)
    public List<TaskResponse> findTasksByProjectId(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        return project.getTasks() == null
                ? Collections.emptyList()
                : project.getTasks()
                .stream()
                .map(this::mapShortTask)
                .toList();
    }


    private TaskResponse mapShortTask(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    private ProjectResponse mapToResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedDate()
        );
    }
}
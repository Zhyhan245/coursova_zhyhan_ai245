package ua.opnu.labwork2.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.opnu.labwork2.task.model.Task;
import ua.opnu.labwork2.task.model.TaskPriority;
import ua.opnu.labwork2.task.model.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findDistinctByUsers_Id(Long userId);

    List<Task> findByProjectId(Long projectId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = :status")
    long countByStatus(@Param("status") TaskStatus status);

    @Query("""
        SELECT t.priority, COUNT(t)
        FROM Task t
        GROUP BY t.priority
    """)
    List<Object[]> countByPriority();
}
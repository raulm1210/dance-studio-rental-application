package SD_project.dance.Repositories;

import SD_project.dance.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByStudioId(Long studioId);
    @Query("SELECT c FROM Course c WHERE c.enrolledPeople < c.maxPeople ORDER BY c.enrolledPeople DESC")
    List<Course> findAvailablePopularCourses();

    @Query("SELECT c FROM Course c WHERE c.enrolledPeople < c.maxPeople ORDER BY c.pricePerHour ASC")
    List<Course> findAvailableCheapestCourses();
}


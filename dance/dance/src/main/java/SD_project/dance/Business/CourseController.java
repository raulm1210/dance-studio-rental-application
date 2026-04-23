package SD_project.dance.Business;

import SD_project.dance.Model.Course;
import SD_project.dance.Repositories.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    private final CourseRepository courseRepo;

    public CourseController(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    // CREATE
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepo.save(course);
    }

    // GET ALL
    @GetMapping
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }
}

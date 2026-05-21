package SD_project.dance.Business;

import SD_project.dance.Model.AppUser;
import SD_project.dance.Model.Course;
import SD_project.dance.Repositories.AppUserRepository;
import SD_project.dance.Repositories.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    private final CourseRepository courseRepo;
    private final AppUserRepository userRepo;

    public CourseController(CourseRepository courseRepo, AppUserRepository userRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
    }

    // CREATE
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepo.save(course);
    }

    @PostMapping("/{courseId}/book/{userId}")
    public Course bookCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        AppUser user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (course.getEnrolledPeople() == null) {
            course.setEnrolledPeople(0);
        }

        if (course.getEnrolledPeople() >= course.getMaxPeople()) {
            throw new RuntimeException("Course is full");
        }

        if (!user.getBookedCourses().contains(course)) {
            user.getBookedCourses().add(course);
            course.setEnrolledPeople(course.getEnrolledPeople() + 1);
        }

        userRepo.save(user);
        return courseRepo.save(course);
    }

    // GET ALL
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    @GetMapping("/studio/{studioId}")
    public List<Course> getCoursesByStudio(@PathVariable Long studioId) {
        return courseRepo.findByStudioId(studioId);
    }

    @GetMapping("/popular")
    public List<Course> getPopularCourses() {
        return courseRepo.findAvailablePopularCourses()
                .stream()
                .limit(5)
                .toList();
    }

    @GetMapping("/cheapest")
    public List<Course> getCheapestCourses() {
        return courseRepo.findAvailableCheapestCourses()
                .stream()
                .limit(3)
                .toList();
    }

    @GetMapping("/{userId}/booked-courses")
    public List<Course> getBookedCourses(@PathVariable Long userId) {
        AppUser user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBookedCourses();
    }
}

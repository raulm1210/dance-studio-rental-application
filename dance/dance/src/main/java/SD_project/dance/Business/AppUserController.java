package SD_project.dance.Business;

import SD_project.dance.Model.AppUser;
import SD_project.dance.Model.Course;
import SD_project.dance.Repositories.AppUserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // allow React
public class AppUserController {

    private final AppUserRepository repo;

    public AppUserController(AppUserRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser user) {
        return repo.save(user);
    }

    @PostMapping("/login")
    public AppUser login(@RequestBody AppUser request) {
        AppUser user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    @PutMapping("/{id}/update-info")
    public AppUser updateInfo(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        AppUser user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String oldPassword = request.get("oldPassword");
        String newUsername = request.get("username");
        String newPassword = request.get("newPassword");

        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Old password incorrect");
        }

        user.setUsername(newUsername);

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }

        //user.setProfileImagePath("default-profile.png");
        return repo.save(user);
    }

    @PutMapping("/{id}/profile-picture")
    public AppUser updateProfileImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        AppUser user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = System.currentTimeMillis() + "_profile_" + image.getOriginalFilename();

        Path uploadPath = Paths.get("uploaded_images/profile_pictures/");
        Files.createDirectories(uploadPath);

        Files.copy(image.getInputStream(), uploadPath.resolve(fileName));
        user.setProfileImagePath(fileName);
        return repo.save(user);
    }

    @GetMapping("/{userId}/booked-courses")
    public List<Course> getBookedCourses(@PathVariable Long userId) {
        AppUser user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBookedCourses();
    }

    @GetMapping("/instructors")
    public List<AppUser> getInstructors() {
        return repo.findByRole("INSTRUCTOR");
    }

}

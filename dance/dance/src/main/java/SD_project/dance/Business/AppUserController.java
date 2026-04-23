package SD_project.dance.Business;

import SD_project.dance.Model.AppUser;
import SD_project.dance.Repositories.AppUserRepository;
import org.springframework.web.bind.annotation.*;

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
}

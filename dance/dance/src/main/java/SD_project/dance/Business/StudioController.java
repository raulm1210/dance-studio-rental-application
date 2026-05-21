package SD_project.dance.Business;

import SD_project.dance.Model.Studio;
import SD_project.dance.Model.StudioImage;
import SD_project.dance.Repositories.StudioImageRepository;
import SD_project.dance.Repositories.StudioRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/studios")
@CrossOrigin(origins = "http://localhost:3000")
public class StudioController {

    private final StudioRepository studioRepo;
    private final StudioImageRepository studioImageRepo;

    public StudioController(StudioRepository studioRepo, StudioImageRepository studioImageRepo) {
        this.studioRepo = studioRepo;
        this.studioImageRepo = studioImageRepo;
    }

    // POST the add a studio information WITHOUT GALLERY !!!!
    @PostMapping
    public Studio createStudio(
            //NAMES from here must match the names given to the formdata in FRONTEND !!!!
            @RequestParam("studio") String studioJson,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "logo", required = false) MultipartFile logo
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // 👇 convert JSON string back to object
        Studio studio = mapper.readValue(studioJson, Studio.class);

        if (image != null && !image.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_studio_card_" + image.getOriginalFilename();

            Path uploadPath = Paths.get("uploaded_images/studio_card_images/");
            Files.createDirectories(uploadPath);

            Files.copy(image.getInputStream(), uploadPath.resolve(fileName));

            studio.setImagePath(fileName);

        }

        if (logo != null && !logo.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_logo_" + logo.getOriginalFilename();
            Path uploadPath = Paths.get("uploaded_images/logo_images/");
            Files.createDirectories(uploadPath);

            Files.copy(logo.getInputStream(), uploadPath.resolve(fileName));

            studio.setLogoPath(fileName);
        }

        return studioRepo.save(studio);
    }

    //POST THE GALLERY !!!!
    @PostMapping(value = "/{id}/gallery", consumes = "multipart/form-data")
    public List<StudioImage> uploadImages(
            @PathVariable Long id,
            @RequestParam("gallery") List<MultipartFile> files
    ) throws IOException {

        Studio studio = studioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Studio not found"));

        String uploadDir = "uploaded_images/studio_galleries/";
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);

        List<StudioImage> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {

                String fileName = System.currentTimeMillis() + "_gallery_" + file.getOriginalFilename();
                Files.copy(file.getInputStream(), uploadPath.resolve(fileName));

                StudioImage img = new StudioImage();
                img.setImagePath(fileName);
                img.setStudio(studio);

                savedImages.add(studioImageRepo.save(img));
            }
        }

        return savedImages;
    }

    // GET information WITHOUT GALLERY !!!!
    @GetMapping
    public List<Studio> getStudios() {
        return studioRepo.findAll();
    }

    @GetMapping("/{id}")
    public Studio getStudioById(@PathVariable Long id) {
        return studioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Studio not found"));
    }

}

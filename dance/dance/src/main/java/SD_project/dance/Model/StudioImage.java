package SD_project.dance.Model;

import jakarta.persistence.*;

@Entity
public class StudioImage {

    @Id
    @GeneratedValue
    private Long id;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}

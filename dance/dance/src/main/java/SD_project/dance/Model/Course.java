package SD_project.dance.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String instructorName;
    private String dayOfWeek;
    private String startTime;
    private Double pricePerHour;
    private Integer enrolledPeople=0;
    private Integer maxPeople;
    private String color;
    private String style;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    @JsonBackReference("studio-courses")
    private Studio studio;

    @ManyToMany(mappedBy = "bookedCourses")
    @JsonIgnore
    private List<AppUser> bookedUsers = new ArrayList<>();

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Integer getEnrolledPeople() {
        return enrolledPeople;
    }

    public void setEnrolledPeople(Integer enrolledPeople) {
        this.enrolledPeople = enrolledPeople;
    }

    public List<AppUser> getBookedUsers() {
        return bookedUsers;
    }

    public void setBookedUsers(List<AppUser> bookedUsers) {
        this.bookedUsers = bookedUsers;
    }
}

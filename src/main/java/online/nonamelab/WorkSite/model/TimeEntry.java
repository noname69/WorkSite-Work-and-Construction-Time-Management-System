package online.nonamelab.WorkSite.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "time_entries")
public class TimeEntry
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

//    private double hours;

//    private double distance;

    @Enumerated(EnumType.STRING)
    private TimeEntryStatus status;

    private boolean locked = false;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "timeEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntrySite> projects = new ArrayList<>();


    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

//    public double getHours() {
//        return hours;
//    }
//
//    public void setHours(double hours) {
//        this.hours = hours;
//    }

//    public double getDistance() {
//        return distance;
//    }
//
//    public void setDistance(double distance) {
//        this.distance = distance;
//    }

    public TimeEntryStatus getStatus() {
        return status;
    }

    public void setStatus(TimeEntryStatus status) {
        this.status = status;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TimeEntrySite> getProjects() {
        return projects;
    }

    public void setProjects(List<TimeEntrySite> projects) {
        this.projects = projects;
    }

//    public ConstructionSite getSite() {
//        return site;
//    }
//
//    public void setSite(ConstructionSite site) {
//        this.site = site;
//    }
}

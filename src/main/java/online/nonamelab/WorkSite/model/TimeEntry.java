package online.nonamelab.WorkSite.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "")
public class TimeEntry
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private double hours;

    private double distance;

    @Enumerated(EnumType.STRING)
    private TimeEntryStatus status;

    private boolean locked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private ConstructionSite site;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

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

    public ConstructionSite getSite() {
        return site;
    }

    public void setSite(ConstructionSite site) {
        this.site = site;
    }
}

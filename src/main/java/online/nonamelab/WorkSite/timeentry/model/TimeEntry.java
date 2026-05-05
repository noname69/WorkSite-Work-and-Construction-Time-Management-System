package online.nonamelab.WorkSite.timeentry.model;

import jakarta.persistence.*;
import online.nonamelab.WorkSite.model.TimeEntryStatus;
import online.nonamelab.WorkSite.user.model.User;

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

    @Enumerated(EnumType.STRING)
    private TimeEntryStatus status;

    private boolean locked = false;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "timeEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntrySite> sites = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TimeEntrySite> getSites() {
        return sites;
    }

    public void setSites(List<TimeEntrySite> sites) {
        this.sites = sites;
    }

}

package com.api.rest.canvas2.Users.domain;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Group.domain.Group;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.ZoomMeeting.domain.ZoomMeeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String email;
    private Role role;
    private String password;
    private String profilePicture;

    @ManyToMany
    @JoinTable(
            name = "section_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private List<Section> sections;

    @ManyToMany
    @JoinTable(
            name = "assignment_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "assignment_id")
    )
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ZoomMeeting> zoomMeetings;

    @ManyToMany
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    @ManyToMany
    @JoinTable(
            name = "course_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}

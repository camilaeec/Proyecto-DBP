package com.api.rest.canvas2.Users.domain;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Assistant.domain.Assistant;
import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Grades.domain.Grade;
import com.api.rest.canvas2.Group.domain.Group;
import com.api.rest.canvas2.Material.domain.Material;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.ZoomMeeting.domain.ZoomMeeting;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String lastname;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private Role role;

    @NotNull
    private String password;


    private String profilePicture = "https://static.vecteezy.com/system/resources/previews/009/734/564/non_2x/default-avatar-profile-icon-of-social-media-user-vector.jpg";

    @ManyToMany(mappedBy = "users")
    private List<Section> sections;

    @ManyToMany(mappedBy = "assignedUsers")
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ZoomMeeting> zoomMeetings;

    @ManyToMany(mappedBy = "users")
    private List<Group> groups;

    @OneToMany(mappedBy = "user")
    private List<Grade> grades;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Material> materials;


}

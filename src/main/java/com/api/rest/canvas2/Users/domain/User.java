package com.api.rest.canvas2.Users.domain;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Assignment.domain.Assignment;
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
    private List<Section> section;

    @ManyToMany
    private List<Assignment> assignments;

    @OneToMany
    private  List<Announcement> announcement;

    @OneToMany
    private List<ZoomMeeting> zoomMeeting;
}

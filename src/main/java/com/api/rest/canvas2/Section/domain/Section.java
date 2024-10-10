package com.api.rest.canvas2.Section.domain;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Material.domain.Material;
import com.api.rest.canvas2.Quiz.domain.Quiz;
import com.api.rest.canvas2.Users.domain.User;
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
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String section;

    private String type;

    @ManyToOne
    private Course course;

    @ManyToMany
    private List<User> user;

    @OneToMany
    private List<Assignment> assignments;

    @OneToMany
    private List<Quiz> quizzes;

    @OneToMany
    private List<Material> materials;

    @OneToMany
    private List<Announcement> announcements;

    @OneToMany
    private List<ZoomMeeting> zoomMeetings;
}

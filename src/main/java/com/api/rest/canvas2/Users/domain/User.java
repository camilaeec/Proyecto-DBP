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
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails {
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
    @Pattern(regexp = "^[a-zA-Z]+\\.[a-zA-Z]+@utec\\.edu\\.pe$", message = "Email must be from the utec.edu.pe domain")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

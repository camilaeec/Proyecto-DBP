package com.api.rest.canvas2.Assistant.domain;

import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Users.domain.User;
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
public class Assistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isExternal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "assistant_section",
            joinColumns = @JoinColumn(name = "assistant_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private List<Section> sections;
}
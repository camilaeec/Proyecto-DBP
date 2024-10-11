package com.api.rest.canvas2.Group.infrastructure;

import com.api.rest.canvas2.Group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findBySectionId(Long sectionId);
}

package com.api.rest.canvas2.Section.infrastructure;

import com.api.rest.canvas2.Section.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

}

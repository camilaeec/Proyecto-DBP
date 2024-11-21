package com.api.rest.canvas2.Material.infrastructure;

import com.api.rest.canvas2.Material.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findBySectionId(Long sectionId);

    List<Material> findByUserId(Long userId);
}

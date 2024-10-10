package com.api.rest.canvas2.Assistant.infrastructure;

import com.api.rest.canvas2.Assistant.domain.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, Long> {
}

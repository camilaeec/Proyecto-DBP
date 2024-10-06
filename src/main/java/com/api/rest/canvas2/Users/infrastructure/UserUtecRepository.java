package com.api.rest.canvas2.Users.infrastructure;

import com.api.rest.canvas2.Users.domain.UserUTEC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserUtecRepository extends JpaRepository<UserUTEC, Long> {
    Optional<UserUTEC> findByName(String name);
}

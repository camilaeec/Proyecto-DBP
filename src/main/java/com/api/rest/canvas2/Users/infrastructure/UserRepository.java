package com.api.rest.canvas2.Users.infrastructure;

import com.api.rest.canvas2.Users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

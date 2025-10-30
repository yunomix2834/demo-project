package com.identity.repository;

import com.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("""
      select u from User u
      where (:username is null or u.username = :username)
        and (:email    is null or u.email    = :email)
      """)
    Optional<User> findByUsernameOrEmail(
            @Param("username") String username,
            @Param("email") String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}

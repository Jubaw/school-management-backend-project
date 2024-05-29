package com.project.repository.user;

import com.project.entity.concretes.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    User findByUsernameEquals(String username);

    boolean existsByUsername(String username);

    boolean existsBySssn(String ssn);

    boolean existsByPhoneNumber(String phone);

    boolean existsByEmail(String email);

    Page<User> findByUserRoleEquals(String userRole, Pageable pageable);

    Page<User> findByUsername(String username, Pageable pageable);
}

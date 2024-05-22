package com.project.contactmessage.repository;

import com.project.contactmessage.entity.ContactMesssage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMesssage,Long> {


    Page<ContactMesssage> findByEmailContaining(String email, Pageable pageable);

    Page<ContactMesssage> searchBySubject(String subject, Pageable pageable);
}

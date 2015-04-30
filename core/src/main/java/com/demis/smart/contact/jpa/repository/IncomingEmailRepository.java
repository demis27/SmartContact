package com.demis.smart.contact.jpa.repository;

import com.demis.smart.contact.jpa.entity.IncomingEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("incomingEmailRepository")
public interface IncomingEmailRepository extends JpaRepository<IncomingEmail, Long> {
}
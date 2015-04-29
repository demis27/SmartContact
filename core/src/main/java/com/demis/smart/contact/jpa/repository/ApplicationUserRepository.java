package com.demis.smart.contact.jpa.repository;

import com.demis.smart.contact.jpa.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("applicationUserRepository")
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
}

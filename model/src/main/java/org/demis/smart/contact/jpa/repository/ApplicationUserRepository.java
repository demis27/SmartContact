package org.demis.smart.contact.jpa.repository;

import org.demis.smart.contact.jpa.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("applicationUserRepository")
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
}

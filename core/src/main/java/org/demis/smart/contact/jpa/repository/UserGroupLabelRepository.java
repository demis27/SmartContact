package org.demis.smart.contact.jpa.repository;

import org.demis.smart.contact.jpa.entity.UserGroupLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userGroupLabelRepository")
public interface UserGroupLabelRepository extends JpaRepository<UserGroupLabel, Long> {

}

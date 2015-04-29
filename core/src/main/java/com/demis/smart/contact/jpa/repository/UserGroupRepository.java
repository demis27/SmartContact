package com.demis.smart.contact.jpa.repository;

import com.demis.smart.contact.jpa.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userGroupRepository")
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

}

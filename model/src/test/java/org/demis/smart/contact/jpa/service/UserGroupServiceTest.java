package org.demis.smart.contact.jpa.service;

import org.demis.smart.contact.jpa.PersistenceJPAConfig;
import org.demis.smart.contact.jpa.entity.UserGroup;
import org.demis.smart.contact.service.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = PersistenceJPAConfig.class)
})
public class UserGroupServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("userGroupRepositoryService")
    public UserGroupRepositoryService repository;

    @Test
    public void create() {

        UserGroup userGroup = createUserGroup();

        repository.create(userGroup);

        List<UserGroup> userGroups = repository.findAll();
        Assert.assertNotNull(userGroups);
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException {
        List<UserGroup> userGroups = repository.findAll();

        for (UserGroup user: userGroups) {
            repository.delete(user.getId());
        }
    }

    public static UserGroup createUserGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName("All agents");
        return userGroup;
    }}


package org.demis.smart.contact.jpa.service;

import org.demis.smart.contact.jpa.PersistenceJPAConfig;
import org.demis.smart.contact.jpa.entity.UserGroup;
import org.demis.smart.contact.jpa.entity.UserGroupLabel;
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
public class UserGroupLabelServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("userGroupLabelRepositoryService")
    public UserGroupLabelRepositoryService userGroupLabelRepositoryService;

    @Autowired
    @Qualifier("userGroupRepositoryService")
    public UserGroupRepositoryService userGroupRepositoryService;

    @Test
    public void create() {
        UserGroup userGroup = UserGroupServiceTest.createUserGroup();
        userGroupRepositoryService.create(userGroup);

        UserGroupLabel userGroupLabel = createUserGroupLabel();
        userGroupLabel.setUserGroup(userGroup);
        userGroupLabelRepositoryService.create(userGroupLabel);


        List<UserGroupLabel> userGroupLabels = userGroupLabelRepositoryService.findAll();
        Assert.assertNotNull(userGroupLabels);
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException {
        List<UserGroupLabel> userGroupLabels = userGroupLabelRepositoryService.findAll();

        for (UserGroupLabel user: userGroupLabels) {
            userGroupLabelRepositoryService.delete(user.getId());
        }

        List<UserGroup> userGroups = userGroupRepositoryService.findAll();

        for (UserGroup user: userGroups) {
            userGroupRepositoryService.delete(user.getId());
        }
    }

    public static UserGroupLabel createUserGroupLabel() {
        UserGroupLabel userGroupLabel = new UserGroupLabel();
        userGroupLabel.setLabel("All agents");
        userGroupLabel.setLocale("en_US");
        return userGroupLabel;
    }}

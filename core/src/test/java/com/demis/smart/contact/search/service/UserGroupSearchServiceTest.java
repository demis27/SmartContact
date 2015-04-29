package com.demis.smart.contact.search.service;

import com.demis.smart.contact.jpa.PersistenceJPAConfig;
import com.demis.smart.contact.search.SearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@ContextHierarchy({
        @ContextConfiguration(classes = SearchConfig.class),
        @ContextConfiguration(classes = PersistenceJPAConfig.class)
})
public class UserGroupSearchServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("userGroupSearchService")
    private UserGroupSearchService userGroupSearchService;

    @Test
    public void create() {
    }

    @AfterMethod
    public void deleteAll() {


    }

}

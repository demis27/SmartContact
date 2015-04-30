package com.demis.smart.contact.jpa.service;

import com.demis.smart.contact.jpa.PersistenceJPAConfig;
import com.demis.smart.contact.jpa.entity.IncomingTransport;
import com.demis.smart.contact.service.ModelNotFoundException;
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
public class IncomingTransportServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("incomingTransportRepositoryService")
    public IncomingTransportRepositoryService repository;

    @Test
    public void create() {

        IncomingTransport incomingTransport = createIncomingTransport();

        repository.create(incomingTransport);

        List<IncomingTransport> incomingTransports = repository.findAll();
        Assert.assertNotNull(incomingTransports);
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException {
        List<IncomingTransport> incomingTransports = repository.findAll();

        for (IncomingTransport incomingTransport : incomingTransports) {
            repository.delete(incomingTransport.getId());
        }
    }

    public static IncomingTransport createIncomingTransport() {
        IncomingTransport incomingTransport = new IncomingTransport();
        incomingTransport.setName("Support");
        return incomingTransport;
    }
}


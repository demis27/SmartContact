package com.demis.smart.contact.jpa.service;

import com.demis.smart.contact.jpa.PersistenceJPAConfig;
import com.demis.smart.contact.jpa.entity.IncomingEmail;
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
public class IncomingEmailServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("incomingEmailRepositoryService")
    public IncomingEmailRepositoryService repository;

    @Autowired
    @Qualifier("incomingTransportRepositoryService")
    public IncomingTransportRepositoryService incomingTransportRepository;

    @Test
    public void create() {

        IncomingEmail incomingEmail = createIncomingEmail();

        incomingTransportRepository.create(incomingEmail.getIncomingTransport());
        repository.create(incomingEmail);

        List<IncomingEmail> incomingEmails = repository.findAll();
        Assert.assertNotNull(incomingEmails);
        Assert.assertNotNull(incomingEmail.getBinaryContent());
    }

    @AfterMethod
    public void deleteAll() throws ModelNotFoundException {
        List<IncomingEmail> incomingEmails = repository.findAll();

        for (IncomingEmail incomingEmail : incomingEmails) {
            repository.delete(incomingEmail.getId());
        }
    }

    public static IncomingEmail createIncomingEmail() {
        IncomingTransport incomingTransport = new IncomingTransport();
        incomingTransport.setName("Marketing");

        IncomingEmail incomingEmail = new IncomingEmail();
        incomingEmail.setIncomingTransport(incomingTransport);
        byte[] binary = new byte[10];
        incomingEmail.setSize(binary.length);
        for (int i = 0; i < 10; i++) {
            binary[i] = 'a';
        }
        incomingEmail.setBinaryContent(binary);

        return incomingEmail;
    }
}


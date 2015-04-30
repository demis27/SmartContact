package com.demis.smart.contact.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.IncomingEmail;
import com.demis.smart.contact.jpa.service.IncomingEmailRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("incomingEmailService")
public class IncomingEmailService {

    @Autowired
    @Qualifier("incomingEmailRepositoryService")
    private IncomingEmailRepositoryService incomingEmailRepositoryService;

    @Transactional
    public IncomingEmail create(IncomingEmail created) {
        IncomingEmail incomingEmail = incomingEmailRepositoryService.create(created);
        return incomingEmail;
    }

    @Transactional
    public IncomingEmail delete(Long id) throws ModelNotFoundException {
        IncomingEmail incomingEmail = incomingEmailRepositoryService.delete(id);
        return incomingEmail;
    }

    @Transactional
    public List<IncomingEmail> findAll() {
        return incomingEmailRepositoryService.findAll();
    }

    @Transactional
    public List<IncomingEmail> findPart(Range range, List<Sort> sorts) {
        return incomingEmailRepositoryService.findPart(range, sorts);
    }

    @Transactional
    public IncomingEmail findById(Long id) {
        return incomingEmailRepositoryService.findById(id);
    }

    @Transactional
    public IncomingEmail update(IncomingEmail updated) throws ModelNotFoundException {
        IncomingEmail incomingEmail = incomingEmailRepositoryService.update(updated);
        return incomingEmail;
    }

}

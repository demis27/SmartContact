package com.demis.smart.contact.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.IncomingTransport;
import com.demis.smart.contact.jpa.service.IncomingTransportRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("incomingTransportService")
public class IncomingTransportService {

    @Autowired
    @Qualifier("incomingTransportRepositoryService")
    private IncomingTransportRepositoryService incomingTransportRepositoryService;

    @Transactional
    public IncomingTransport create(IncomingTransport created) {
        IncomingTransport incomingTransport = incomingTransportRepositoryService.create(created);
        return incomingTransport;
    }

    @Transactional
    public IncomingTransport delete(Long id) throws ModelNotFoundException {
        IncomingTransport incomingTransport = incomingTransportRepositoryService.delete(id);
        return incomingTransport;
    }

    @Transactional
    public List<IncomingTransport> findAll() {
        return incomingTransportRepositoryService.findAll();
    }

    @Transactional
    public List<IncomingTransport> findPart(Range range, List<Sort> sorts) {
        return incomingTransportRepositoryService.findPart(range, sorts);
    }

    @Transactional
    public IncomingTransport findById(Long id) {
        return incomingTransportRepositoryService.findById(id);
    }

    @Transactional
    public IncomingTransport update(IncomingTransport updated) throws ModelNotFoundException {
        IncomingTransport incomingTransport = incomingTransportRepositoryService.update(updated);
        return incomingTransport;
    }
}

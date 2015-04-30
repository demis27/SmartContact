package com.demis.smart.contact.jpa.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.IncomingTransport;
import com.demis.smart.contact.jpa.repository.IncomingTransportRepository;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.converter.SortConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="incomingTransportRepositoryService")
public class IncomingTransportRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingTransportRepositoryService.class);

    @Resource(name = "incomingTransportRepository")
    private IncomingTransportRepository incomingTransportRepository;

    @Transactional
    public IncomingTransport create(IncomingTransport created) {
        return incomingTransportRepository.save(created);
    }

    public IncomingTransport delete(Long id) throws ModelNotFoundException {
        IncomingTransport deleted = incomingTransportRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No model found with id: " + id);
            throw new ModelNotFoundException();
        }

        incomingTransportRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<IncomingTransport> findAll() {
        return incomingTransportRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<IncomingTransport> findPart(Range range, List<Sort> sorts) {
        return incomingTransportRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }


    @Transactional(readOnly = true)
    public IncomingTransport findById(Long id) {
        return incomingTransportRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public IncomingTransport update(IncomingTransport updated) throws ModelNotFoundException {
        IncomingTransport user = incomingTransportRepository.findOne(updated.getId());

        if (user == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            incomingTransportRepository.save(updated);
        }

        return user;

    }
}

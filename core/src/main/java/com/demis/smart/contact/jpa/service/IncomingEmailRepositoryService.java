package com.demis.smart.contact.jpa.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.IncomingEmail;
import com.demis.smart.contact.jpa.repository.IncomingEmailRepository;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.converter.SortConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="incomingEmailRepositoryService")
public class IncomingEmailRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingEmailRepositoryService.class);

    @Resource(name = "incomingEmailRepository")
    private IncomingEmailRepository incomingEmailRepository;

    @Transactional
    public IncomingEmail create(IncomingEmail created) {
        return incomingEmailRepository.save(created);
    }

    public IncomingEmail delete(Long id) throws ModelNotFoundException {
        IncomingEmail deleted = incomingEmailRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No model found with id: " + id);
            throw new ModelNotFoundException();
        }

        incomingEmailRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<IncomingEmail> findAll() {
        return incomingEmailRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<IncomingEmail> findPart(Range range, List<Sort> sorts) {
        return incomingEmailRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }


    @Transactional(readOnly = true)
    public IncomingEmail findById(Long id) {
        return incomingEmailRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public IncomingEmail update(IncomingEmail updated) throws ModelNotFoundException {
        IncomingEmail user = incomingEmailRepository.findOne(updated.getId());

        if (user == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            incomingEmailRepository.save(updated);
        }

        return user;

    }
}

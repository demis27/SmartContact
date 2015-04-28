package org.demis.smart.contact.jpa.service;

import org.demis.smart.contact.Range;
import org.demis.smart.contact.Sort;
import org.demis.smart.contact.jpa.entity.ApplicationUser;
import org.demis.smart.contact.jpa.repository.ApplicationUserRepository;
import org.demis.smart.contact.service.ModelNotFoundException;
import org.demis.smart.contact.service.converter.SortConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class ApplicationUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserService.class);

    @Resource(name = "applicationUserRepository")
    private ApplicationUserRepository applicationUserRepository;

    @Transactional
    public ApplicationUser create(ApplicationUser created) {
        return applicationUserRepository.save(created);
    }

    public ApplicationUser delete(Long id) throws ModelNotFoundException {
        ApplicationUser deleted = applicationUserRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No User found with id: " + id);
            throw new ModelNotFoundException();
        }

        applicationUserRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<ApplicationUser> findAll() {
        return applicationUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ApplicationUser> findPart(Range range, List<Sort> sorts) {
        return applicationUserRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }


    @Transactional(readOnly = true)
    public ApplicationUser findById(Long id) {
        return applicationUserRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public ApplicationUser update(ApplicationUser updated) throws ModelNotFoundException {
        ApplicationUser user = applicationUserRepository.findOne(updated.getId());

        if (user == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            applicationUserRepository.save(updated);
        }

        return user;

    }
}


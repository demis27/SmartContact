package com.demis.smart.contact.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.ApplicationUser;
import com.demis.smart.contact.jpa.service.ApplicationUserRepositoryService;
import com.demis.smart.contact.search.service.ApplicationUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("applicationUserService")
public class ApplicationUserService {

    @Autowired
    @Qualifier("applicationUserRepositoryService")
    private ApplicationUserRepositoryService applicationUserRepositoryService;

    @Autowired
    @Qualifier ("applicationUserSearchService")
    private ApplicationUserSearchService applicationUserSearchService;

    @Transactional
    public ApplicationUser create(ApplicationUser created) {
        ApplicationUser ApplicationUser = applicationUserRepositoryService.create(created);
        applicationUserSearchService.create(ApplicationUser);
        return ApplicationUser;
    }

    @Transactional
    public ApplicationUser delete(Long id) throws ModelNotFoundException {
        ApplicationUser ApplicationUser = applicationUserRepositoryService.delete(id);
        applicationUserSearchService.delete(id);
        return ApplicationUser;
    }

    @Transactional
    public List<ApplicationUser> findAll() {
        return applicationUserRepositoryService.findAll();
    }

    @Transactional
    public List<ApplicationUser> findPart(Range range, List<Sort> sorts) {
        return applicationUserRepositoryService.findPart(range, sorts);
    }

    @Transactional
    public ApplicationUser findById(Long id) {
        return applicationUserRepositoryService.findById(id);
    }

    @Transactional
    public ApplicationUser update(ApplicationUser updated) throws ModelNotFoundException {
        ApplicationUser ApplicationUser = applicationUserRepositoryService.update(updated);
        applicationUserSearchService.update(ApplicationUser);
        return ApplicationUser;
    }
}

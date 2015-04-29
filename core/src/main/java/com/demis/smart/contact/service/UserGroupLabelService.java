package com.demis.smart.contact.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.UserGroupLabel;
import com.demis.smart.contact.jpa.service.UserGroupLabelRepositoryService;
import com.demis.smart.contact.search.service.UserGroupLabelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userGroupLabelService")
public class UserGroupLabelService {

    @Autowired
    @Qualifier("userGroupLabelRepositoryService")
    private UserGroupLabelRepositoryService userGroupLabelRepositoryService;

    @Autowired
    @Qualifier ("userGroupLabelSearchService")
    private UserGroupLabelSearchService UserGroupLabelSearchService;

    @Transactional
    public UserGroupLabel create(UserGroupLabel created) {
        UserGroupLabel UserGroupLabel = userGroupLabelRepositoryService.create(created);
        UserGroupLabelSearchService.create(UserGroupLabel);
        return UserGroupLabel;
    }

    @Transactional
    public UserGroupLabel delete(Long id) throws ModelNotFoundException {
        UserGroupLabel UserGroupLabel = userGroupLabelRepositoryService.delete(id);
        UserGroupLabelSearchService.delete(id);
        return UserGroupLabel;
    }

    @Transactional
    public List<UserGroupLabel> findAll() {
        return userGroupLabelRepositoryService.findAll();
    }

    @Transactional
    public List<UserGroupLabel> findPart(Range range, List<Sort> sorts) {
        return userGroupLabelRepositoryService.findPart(range, sorts);
    }

    @Transactional
    public UserGroupLabel findById(Long id) {
        return userGroupLabelRepositoryService.findById(id);
    }

    @Transactional
    public UserGroupLabel update(UserGroupLabel updated) throws ModelNotFoundException {
        UserGroupLabel UserGroupLabel = userGroupLabelRepositoryService.update(updated);
        UserGroupLabelSearchService.update(UserGroupLabel);
        return UserGroupLabel;
    }
}

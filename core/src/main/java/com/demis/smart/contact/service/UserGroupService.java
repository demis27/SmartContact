package com.demis.smart.contact.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.UserGroup;
import com.demis.smart.contact.jpa.service.UserGroupRepositoryService;
import com.demis.smart.contact.search.service.UserGroupSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userGroupService")
public class UserGroupService {

    @Autowired
    @Qualifier("userGroupRepositoryService")
    private UserGroupRepositoryService userGroupRepositoryService;

    @Autowired
    @Qualifier ("userGroupSearchService")
    private UserGroupSearchService userGroupSearchService;

    @Transactional
    public UserGroup create(UserGroup created) {
        UserGroup userGroup = userGroupRepositoryService.create(created);
        userGroupSearchService.create(userGroup);
        return userGroup;
    }

    @Transactional
    public UserGroup delete(Long id) throws ModelNotFoundException {
        UserGroup userGroup = userGroupRepositoryService.delete(id);
        userGroupSearchService.delete(id);
        return userGroup;
    }

    @Transactional
    public List<UserGroup> findAll() {
        return userGroupRepositoryService.findAll();
    }

    @Transactional
    public List<UserGroup> findPart(Range range, List<Sort> sorts) {
        return userGroupRepositoryService.findPart(range, sorts);
    }

    @Transactional
    public UserGroup findById(Long id) {
        return userGroupRepositoryService.findById(id);
    }

    @Transactional
    public UserGroup update(UserGroup updated) throws ModelNotFoundException {
        UserGroup userGroup = userGroupRepositoryService.update(updated);
        userGroupSearchService.update(userGroup);
        return userGroup;
    }
}

package com.demis.smart.contact.jpa.service;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.UserGroup;
import com.demis.smart.contact.jpa.repository.UserGroupRepository;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.converter.SortConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="userGroupRepositoryService")
public class UserGroupRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupRepositoryService.class);

    @Resource(name = "userGroupRepository")
    private UserGroupRepository userGroupRepository;

    @Transactional
    public UserGroup create(UserGroup created) {
        return userGroupRepository.save(created);
    }

    public UserGroup delete(Long id) throws ModelNotFoundException {
        UserGroup deleted = userGroupRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No model found with id: " + id);
            throw new ModelNotFoundException();
        }

        userGroupRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<UserGroup> findAll() {
        return userGroupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<UserGroup> findPart(Range range, List<Sort> sorts) {
        return userGroupRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }


    @Transactional(readOnly = true)
    public UserGroup findById(Long id) {
        return userGroupRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public UserGroup update(UserGroup updated) throws ModelNotFoundException {
        UserGroup user = userGroupRepository.findOne(updated.getId());

        if (user == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            userGroupRepository.save(updated);
        }

        return user;

    }
}

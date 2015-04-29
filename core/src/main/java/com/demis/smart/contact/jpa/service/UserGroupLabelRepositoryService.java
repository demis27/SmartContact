package com.demis.smart.contact.jpa.service;

import com.demis.smart.contact.jpa.repository.UserGroupLabelRepository;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.converter.SortConverter;
import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.UserGroupLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value ="userGroupLabelRepositoryService")
public class UserGroupLabelRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupLabelRepositoryService.class);

    @Resource(name = "userGroupLabelRepository")
    private UserGroupLabelRepository userGroupLabelRepository;

    @Transactional
    public UserGroupLabel create(UserGroupLabel created) {
        return userGroupLabelRepository.save(created);
    }

    public UserGroupLabel delete(Long id) throws ModelNotFoundException {
        UserGroupLabel deleted = userGroupLabelRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No User found with id: " + id);
            throw new ModelNotFoundException();
        }

        userGroupLabelRepository.delete(deleted);
        return deleted;

    }

    @Transactional(readOnly = true)
    public List<UserGroupLabel> findAll() {
        return userGroupLabelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<UserGroupLabel> findPart(Range range, List<Sort> sorts) {
        return userGroupLabelRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }


    @Transactional(readOnly = true)
    public UserGroupLabel findById(Long id) {
        return userGroupLabelRepository.findOne(id);
    }

    @Transactional(rollbackFor = ModelNotFoundException.class)
    public UserGroupLabel update(UserGroupLabel updated) throws ModelNotFoundException {
        UserGroupLabel user = userGroupLabelRepository.findOne(updated.getId());

        if (user == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new ModelNotFoundException();
        } else {
            userGroupLabelRepository.save(updated);
        }

        return user;

    }
}

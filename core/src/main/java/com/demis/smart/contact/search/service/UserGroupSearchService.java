package com.demis.smart.contact.search.service;

import com.demis.smart.contact.jpa.entity.UserGroup;
import com.demis.smart.contact.search.converter.UserGroupConverter;
import com.demis.smart.contact.search.dto.UserGroupESDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "userGroupSearchService")
public class UserGroupSearchService extends AbstractSearchService<UserGroup, UserGroupESDTO> {

    public static final String USER_MAPPING = "user";

    @Autowired
    @Qualifier("userGroupConverterSearch")
    private UserGroupConverter userGroupConverter;

    @Override
    protected UserGroupConverter getConverter() {
        return userGroupConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }

    @Override
    protected Class<UserGroupESDTO> getDTOClass() {
        return UserGroupESDTO.class;
    }
}
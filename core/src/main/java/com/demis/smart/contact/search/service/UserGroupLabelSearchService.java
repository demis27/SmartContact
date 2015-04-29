package com.demis.smart.contact.search.service;

import com.demis.smart.contact.jpa.entity.UserGroupLabel;
import com.demis.smart.contact.search.converter.UserGroupLabelConverter;
import com.demis.smart.contact.search.dto.UserGroupLabelESDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "userGroupLabelSearchService")
public class UserGroupLabelSearchService extends AbstractSearchService<UserGroupLabel, UserGroupLabelESDTO> {

    public static final String USER_MAPPING = "user";

    @Autowired
    @Qualifier("userGroupLabelConverterSearch")
    private UserGroupLabelConverter userGroupLabelConverter;

    @Override
    protected UserGroupLabelConverter getConverter() {
        return userGroupLabelConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }

    @Override
    protected Class<UserGroupLabelESDTO> getDTOClass() {
        return UserGroupLabelESDTO.class;
    }
}
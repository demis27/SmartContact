package com.demis.smart.contact.web.converter;

import com.demis.smart.contact.jpa.entity.UserGroup;
import com.demis.smart.contact.web.dto.UserGroupDTOWeb;
import org.springframework.stereotype.Service;

@Service(value = "userGroupConverterWeb")
public class UserGroupConverterWeb extends GenericConverterWeb<UserGroup, UserGroupDTOWeb> {

    public UserGroupConverterWeb() {
        super(UserGroup.class, UserGroupDTOWeb.class);
    }

    protected void updateModelFields(UserGroup model, UserGroupDTOWeb dto) {
        model.setName(dto.getName());
    }

    protected void updateDTOFields(UserGroupDTOWeb dto, UserGroup model) {
        dto.setName(model.getName());
    }
}
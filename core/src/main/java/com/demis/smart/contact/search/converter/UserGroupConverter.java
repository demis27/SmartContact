package com.demis.smart.contact.search.converter;

import com.demis.smart.contact.jpa.entity.UserGroup;
import com.demis.smart.contact.search.dto.UserGroupESDTO;
import org.springframework.stereotype.Service;

@Service(value = "userGroupConverterSearch")
public class UserGroupConverter extends GenericConverter<UserGroup, UserGroupESDTO> {

    public UserGroupConverter() {
        super(UserGroup.class, UserGroupESDTO.class);
    }

    @Override
    protected void updateModelFields(UserGroup model, UserGroupESDTO dto) {
        model.setName(dto.getName());
    }

    @Override
    protected void updateDTOFields(UserGroupESDTO dto, UserGroup model) {
        dto.setName(model.getName());
    }
}

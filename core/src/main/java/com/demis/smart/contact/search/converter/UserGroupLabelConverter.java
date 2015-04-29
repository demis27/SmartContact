package com.demis.smart.contact.search.converter;

import com.demis.smart.contact.jpa.entity.UserGroupLabel;
import com.demis.smart.contact.search.dto.UserGroupLabelESDTO;
import org.springframework.stereotype.Service;

@Service(value = "userGroupLabelConverterSearch")
public class UserGroupLabelConverter extends GenericConverter<UserGroupLabel, UserGroupLabelESDTO> {

    public UserGroupLabelConverter() {
        super(UserGroupLabel.class, UserGroupLabelESDTO.class);
    }

    @Override
    protected void updateModelFields(UserGroupLabel model, UserGroupLabelESDTO dto) {
        model.setLabel(dto.getLabel());
        model.setLocale(dto.getLocale());
    }

    @Override
    protected void updateDTOFields(UserGroupLabelESDTO dto, UserGroupLabel model) {
        dto.setLabel(model.getLabel());
        dto.setLocale(model.getLocale());
    }
}


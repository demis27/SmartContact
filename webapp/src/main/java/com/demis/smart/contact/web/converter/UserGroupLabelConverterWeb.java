package com.demis.smart.contact.web.converter;

import com.demis.smart.contact.jpa.entity.UserGroupLabel;
import com.demis.smart.contact.web.dto.UserGroupLabelDTOWeb;
import org.springframework.stereotype.Service;

@Service(value = "userGroupLabelConverterWeb")
public class UserGroupLabelConverterWeb extends GenericConverterWeb<UserGroupLabel, UserGroupLabelDTOWeb> {

    public UserGroupLabelConverterWeb() {
        super(UserGroupLabel.class, UserGroupLabelDTOWeb.class);
    }

    protected void updateModelFields(UserGroupLabel UserGroupLabel, UserGroupLabelDTOWeb UserGroupLabelDTO) {
    }

    protected void updateDTOFields(UserGroupLabelDTOWeb UserGroupLabelDTO, UserGroupLabel UserGroupLabel) {
    }
}


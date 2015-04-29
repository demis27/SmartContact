package com.demis.smart.contact.web.dto;

import com.demis.smart.contact.dto.UserGroupLabelDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserGroupLabelDTOWeb extends UserGroupLabelDTO implements DTOWeb {

    private String href;

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "userGroupLabel";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

}
package com.demis.smart.contact.web.dto;

import com.demis.smart.contact.dto.UserGroupDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserGroupDTOWeb extends UserGroupDTO implements DTOWeb {

    private String href;

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "userGroup";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

}
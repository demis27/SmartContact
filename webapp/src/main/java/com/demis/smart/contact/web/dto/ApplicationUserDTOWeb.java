package com.demis.smart.contact.web.dto;

import com.demis.smart.contact.dto.ApplicationUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApplicationUserDTOWeb extends ApplicationUserDTO implements DTOWeb {

    private String href;

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "applicationUser";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

}
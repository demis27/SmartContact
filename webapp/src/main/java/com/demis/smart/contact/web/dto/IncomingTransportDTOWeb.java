package com.demis.smart.contact.web.dto;

import com.demis.smart.contact.dto.IncomingTransportDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class IncomingTransportDTOWeb extends IncomingTransportDTO implements DTOWeb {

    private String href;

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "incomingTransport";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

}

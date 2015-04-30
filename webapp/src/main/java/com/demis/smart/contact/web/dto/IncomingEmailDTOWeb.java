package com.demis.smart.contact.web.dto;

import com.demis.smart.contact.dto.IncomingEmailDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class IncomingEmailDTOWeb extends IncomingEmailDTO implements DTOWeb {

    private String href;
    private DTOWeb transport;
    private Long transportId;

    @Override
    @JsonIgnore
    public String getResourceName() {
        return "incomingEmail";
    }

    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    public DTOWeb getTransport() {
        return transport;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
        transport = new ResourceReferenceDTOWeb("transport", transportId);
    }

}
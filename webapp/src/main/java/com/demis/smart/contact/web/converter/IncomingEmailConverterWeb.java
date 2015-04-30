package com.demis.smart.contact.web.converter;

import com.demis.smart.contact.jpa.entity.IncomingEmail;
import com.demis.smart.contact.web.dto.DTOWeb;
import com.demis.smart.contact.web.dto.IncomingEmailDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service(value = "incomingEmailConverterWeb")
public class IncomingEmailConverterWeb extends GenericConverterWeb<IncomingEmail, IncomingEmailDTOWeb> {

    public IncomingEmailConverterWeb() {
        super(IncomingEmail.class, IncomingEmailDTOWeb.class);
    }

    protected void updateModelFields(IncomingEmail model, IncomingEmailDTOWeb dto) {
        model.setSize(dto.getSize());
        model.setBinaryContent(dto.getBinaryContent());
    }

    protected void updateDTOFields(IncomingEmailDTOWeb dto, IncomingEmail model) {
        dto.setSize(model.getSize());
        dto.setBinaryContent(model.getBinaryContent());
        dto.setTransportId(model.getIncomingTransport().getId());
    }

    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
        generateHrefForResource(((IncomingEmailDTOWeb) dto).getTransport(), request);
    }

}

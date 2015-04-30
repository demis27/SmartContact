package com.demis.smart.contact.web.converter;

import com.demis.smart.contact.jpa.entity.IncomingTransport;
import com.demis.smart.contact.web.dto.IncomingTransportDTOWeb;
import org.springframework.stereotype.Service;

@Service(value = "incomingTransportConverterWeb")
public class IncomingTransportConverterWeb extends GenericConverterWeb<IncomingTransport, IncomingTransportDTOWeb> {

    public IncomingTransportConverterWeb() {
        super(IncomingTransport.class, IncomingTransportDTOWeb.class);
    }

    protected void updateModelFields(IncomingTransport model, IncomingTransportDTOWeb dto) {
        model.setName(dto.getName());
    }

    protected void updateDTOFields(IncomingTransportDTOWeb dto, IncomingTransport model) {
        dto.setName(model.getName());
    }
}

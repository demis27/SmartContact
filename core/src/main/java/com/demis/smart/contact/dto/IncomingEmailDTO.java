package com.demis.smart.contact.dto;

import com.demis.smart.contact.jpa.entity.IncomingTransport;

public class IncomingEmailDTO implements DTO {

    private Long id = null;
    private Integer size = null;
    private IncomingTransport incomingTransport = null;
    private byte[] binaryContent = null;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }
}

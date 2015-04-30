package com.demis.smart.contact.dto;

public class IncomingTransportDTO implements DTO {

    private Long id = null;
    private String name = null;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

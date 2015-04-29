package com.demis.smart.contact.dto;

public class UserGroupLabelDTO implements DTO {

    private Long id = null;
    private String label = null;
    private String locale = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}

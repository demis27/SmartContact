package com.demis.smart.contact.dto;


import java.util.ArrayList;
import java.util.List;

public class UserGroupDTO implements DTO {

    private Long id = null;
    private String name = null;
    private List<UserGroupLabelDTO> labels = new ArrayList<UserGroupLabelDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserGroupLabelDTO> getLabels() {
        return labels;
    }

    public void setLabels(List<UserGroupLabelDTO> labels) {
        this.labels = labels;
    }
}

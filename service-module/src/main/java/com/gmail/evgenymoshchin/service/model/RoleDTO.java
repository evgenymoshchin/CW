package com.gmail.evgenymoshchin.service.model;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;

public class RoleDTO {

    private RoleEnum name;

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}

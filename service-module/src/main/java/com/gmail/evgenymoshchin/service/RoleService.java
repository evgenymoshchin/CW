package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.model.RoleDTO;

import java.util.List;

public interface RoleService {

    void addRole(RoleEnum roleName);

    List<RoleDTO> getRoles();
}

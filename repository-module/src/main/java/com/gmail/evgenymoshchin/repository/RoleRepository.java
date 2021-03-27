package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;

import java.sql.Connection;

public interface RoleRepository extends GenericRepository<Role>{
    Role getRoleByName(Connection connection, RoleEnum roleName);
}

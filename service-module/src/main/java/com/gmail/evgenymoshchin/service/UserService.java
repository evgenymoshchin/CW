package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.UserDTO;

import java.util.List;

public interface UserService {

    void addUser(UserDTO userDTO);

    List<UserDTO> getAll();

    void deleteUserById(Long userId);

    boolean isValidUser(String email, String password);

    UserDTO findUserByEmail(String email);
}

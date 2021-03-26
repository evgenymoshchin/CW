package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserReviewDTO;

import java.util.List;

public interface UserReviewService {

    void addUserReview(UserReviewDTO userReviewDTO);

    List<UserReviewDTO> getAll();

    void deleteUserReviewById(Long userId);
}

package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Dto.UserDto;
import com.webgroupEproject.myproject23526.Model.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}

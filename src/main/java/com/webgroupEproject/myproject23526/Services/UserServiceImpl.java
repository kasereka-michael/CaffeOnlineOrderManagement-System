package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Dto.UserDto;
import com.webgroupEproject.myproject23526.Model.Role;
import com.webgroupEproject.myproject23526.Model.User;
import com.webgroupEproject.myproject23526.Repository.RoleRepository;
import com.webgroupEproject.myproject23526.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFullname());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Random random = new Random();
        String[] all = {"ROLE_ADMIN","ROLE_USER"};
        int randomIndex = random.nextInt(all.length);
        String roleName = all[randomIndex];
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        System.out.println("implement i iam there is not such user"+ userDto.getPassword()+  " "+ userDto.getEmail()+" "+   userDto.getFullname());

        userRepository.save(user);


    }

    @Override
    public User findUserByEmail(String email) {
        if(userRepository.findByEmail(email)==null){
            System.out.println("there is not such user");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFullname(str[0]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist(){
        Random random = new Random();
        String[] all = {"ROLE_ADMIN","ROLE_USER"};
        int randomIndex = random.nextInt(all.length);
        String roleName = all[randomIndex];
        Role role = new Role();
        role.setName(roleName);
        System.out.println("has this role "+roleName);
        return roleRepository.save(role);
    }

}

package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.User;
import com.example.project3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



//    public void addUser(User user) {
//        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setRole("ADMIN");
//        user.setPassword(hash);
//        userRepository.save(user);
//    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }
    public void updateUser(Integer id, User user) {
        User updatedUser = userRepository.findUserById(id);

        if (updatedUser == null){
            throw new ApiException("User not found");
        }

        updatedUser.setUsername(user.getUsername());
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        updatedUser.setPassword(hash);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setName(user.getName());
        userRepository.save(updatedUser);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);

        if (user == null){
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }

}

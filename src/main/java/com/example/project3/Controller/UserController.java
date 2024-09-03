package com.example.project3.Controller;

import com.example.project3.Model.User;
import com.example.project3.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor

public class UserController {

    public final UserService userService;


    //    @PostMapping("/register")
//    public ResponseEntity register (@RequestBody User user) {
//        userService.register(user);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                "User added"
//        );
//    }
    @GetMapping("/get/users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.getAllUsers()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody User user, @PathVariable Integer id) {

        userService.updateUser(id, user);

        return ResponseEntity.status(200).body(
                "user updated successfully"
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                "user deleted successfully"
        );

    }


}


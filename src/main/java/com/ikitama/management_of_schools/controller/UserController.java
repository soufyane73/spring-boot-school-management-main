package com.ikitama.management_of_schools.controller;

import com.ikitama.management_of_schools.domain.Subject;
import com.ikitama.management_of_schools.domain.User;
import com.ikitama.management_of_schools.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {

    UserService userService;

    @PutMapping(value = "update")
    public ResponseEntity<User> updateSubject(@Validated @RequestBody User user){
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @PostMapping(value = "/change-password/{ID}")
    public ResponseEntity<User> changePassword(
            @PathVariable("ID") Long ID,
            @RequestParam("newPassword") String newPassword
    ) {
        return ResponseEntity.ok().body(userService.changePassword(ID, newPassword));
    }
    @GetMapping(value = "/{ID}")
    public ResponseEntity<User> getUser(@PathVariable("ID") Long ID){
        return ResponseEntity.ok().body(userService.getUser(ID));
    }

    @GetMapping(value = "/getByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @GetMapping(value = "/getByName")
    public ResponseEntity<User> getUserByName(@RequestParam("name") String name){
        return ResponseEntity.ok().body(userService.getUserByName(name));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @DeleteMapping(value = "{ID}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable("ID") Long ID){
        userService.deleteUser(ID);
        return ResponseEntity.ok().build();
    }
}

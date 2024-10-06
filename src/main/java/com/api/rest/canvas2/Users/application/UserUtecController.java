package com.api.rest.canvas2.Users.application;

import com.api.rest.canvas2.Users.domain.UserUTEC;
import com.api.rest.canvas2.Users.domain.UserUtecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserUtecController {
    @Autowired
    private final UserUtecService userUtecService;

    public UserUtecController(UserUtecService userUtecService) {
        this.userUtecService = userUtecService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserUTEC>> getAllUsers(){
            return ResponseEntity.ok(userUtecService.findAll());
    }

    @GetMapping("/findBy/{id}")
    public ResponseEntity<UserUTEC> findById(@PathVariable Long id){
        return ResponseEntity.ok(userUtecService.findById(id));
    }
}

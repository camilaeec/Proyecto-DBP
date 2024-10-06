package com.api.rest.canvas2.Users.domain;

import com.api.rest.canvas2.Users.infrastructure.UserUtecRepository;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class UserUtecService {
    @Autowired
    private final UserUtecRepository userUtecRepository;
    private final ModelMapper modelMapper;

    public UserUtecService(UserUtecRepository userUtecRepository, ModelMapper modelMapper) {
        this.userUtecRepository = userUtecRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserUTEC> findAll(){
        List<UserUTEC> userUTEC = userUtecRepository.findAll();
        return userUTEC;
    }

    public UserUTEC findById(Long id){
        UserUTEC userUTEC = userUtecRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with the id " + id + " not found"));
        return userUTEC;
    }

    public UserUTEC createUser(UserUTEC userUTEC){
        UserUTEC userUTEC1 = new UserUTEC();
        userUTEC1.setName(userUTEC.getName());
        userUTEC1.setLastname(userUTEC.getLastname());
        userUTEC1.setEmail(userUTEC.getEmail());
        userUTEC1.setPassword(userUTEC.getPassword());
        userUTEC1.setBirthday(userUTEC.getBirthday());
        userUtecRepository.save(userUTEC1);
        return userUTEC1;
    }

    public UserUTEC updateUser(Long id, UserUTEC userUTEC){
        UserUTEC user = userUtecRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with the id " + id + " not found"));
        user.setName(userUTEC.getName());
        user.setLastname(userUTEC.getLastname());
        user.setEmail(userUTEC.getEmail());
        user.setPassword(userUTEC.getPassword());
        user.setBirthday(userUTEC.getBirthday());
        userUtecRepository.save(user);
        return user;
    }

    public void deleteUser(Long id){
        UserUTEC userUTEC = userUtecRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with the id " + id + " not found"));
        userUtecRepository.delete(userUTEC);
    }
}

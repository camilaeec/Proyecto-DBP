package com.api.rest.canvas2.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class beans {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

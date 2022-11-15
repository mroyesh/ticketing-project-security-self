package com.cydeo;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication  //this includes @Configuration
public class TicketingProjectSecurityApplication {

    public static void main(String[] args) {



        SpringApplication.run(TicketingProjectSecurityApplication.class, args);
    }
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();

    }



//    this method encode our row password and save to DB

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
//       responsibility--> this method takes the row password and changes to encoded password
//        based on spring method
    }

}

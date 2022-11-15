package com.cydeo.config;


import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//        List<UserDetails> userList= new ArrayList<>();

//     what is userDetailsService?
//     business logic.
//
//        userList.add(
//                new User("mike", encoder.encode("password")
//                        , Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//
//        new User("OZZY", encoder.encode("password")
//                , Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//        return  new InMemoryUserDetailsManager(userList);
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasRole("Manager")// hasRole is used when we follow up exactly naming in DB
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
//                .antMatchers("/user/**").hasRole("ADMIN") THIS LINE WITH TOP LINE ARE SAME

//                .antMatchers("/project/**").hasAuthority("MANAGER")


//
//                .antMatchers("/task/employee/**").hasRole("EMPLOYEE")

//
//                .antMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//                .antMatchers("/task/**").hasAnyRole("ROLE_EMPLOYEE")// in this scenario we take our data from method we created at the top of this class.
                .antMatchers("/task/employee/**").hasAuthority("Employee")
//                if you need to wright hasAuthority we need to right ROLE_EMPLOYEE

                .antMatchers(
                        "/",
                "/login",
                "/fragments/**",
                "/assets/**",
                "/images/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic()
                .formLogin()
                .loginPage("/login")
//                .defaultSuccessUrl("/welcome")
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout")
                .and()
                .rememberMe()
                .tokenValiditySeconds(120)
                .key("cydeo")
                .userDetailsService(securityService)
                .and()

                .build();
    }



}

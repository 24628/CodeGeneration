package io.swagger.service.auth;

import io.swagger.enums.UserType;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IUserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegisterService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(String email, String username, String password, long dayLimit)
    {
        String token = "";

        if(email.isEmpty() || username.isEmpty() || password.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Missing content");

        UserEntity userExist = userRepository.findByUsername(username);
        if(userExist != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already exist in the database");

//        if(EmailValidator.getInstance().isValid(email))
//            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email is invalid");

        try {
            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setType(UserType.CUSTOMER);
            user.setDay_limit(dayLimit);
            user.setTransaction_limit(500L);

            userRepository.save(user);

            System.out.println("LOL");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getType());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password");
        }
        return token;
    }
}

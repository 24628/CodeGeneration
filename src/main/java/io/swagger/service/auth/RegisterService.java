package io.swagger.service.auth;

import io.swagger.api.exceptions.AuthorizationException;
import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.Roles;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IUserDTO;
import io.swagger.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    IUserDTO userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(String email, String username, String password, long dayLimit)
    {
        String token = "";

        if(email.isEmpty() || username.isEmpty() || password.isEmpty())
            throw new ValidationException("Missing content");

        if(Validator.containsWhiteSpace(email) || Validator.containsWhiteSpace(username) || Validator.containsWhiteSpace(password))
            throw new ValidationException("No white Spaces!");

        UserEntity userExist = userRepository.findByUsername(username);
        if(userExist != null)
            throw new EntityAlreadyExistException("Username already exist in the database");

//        if(EmailValidator.getInstance().isValid(email))
//            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email is invalid");

        try {
            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Roles.CUSTOMER);
            user.setDay_limit(dayLimit);
            user.setTransaction_limit(500L);

            userRepository.save(user);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRole());
        } catch (AuthenticationException e) {
            throw new AuthorizationException();
        }
        return token;
    }
}

package io.swagger.service.auth;

import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.Roles;
import io.swagger.helpers.AuthResult;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.Responses.UserLoginEntity;
import io.swagger.model.Request.RegisterRequest;
import io.swagger.repository.IUserDTO;
import io.swagger.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    IUserDTO userDTO;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Validator validator;

    public AuthResult register(RegisterRequest body) throws AuthenticationException, ValidationException, EntityAlreadyExistException {
        UserEntity user = new UserEntity();

        validator.CanCreateUser(body.getUsername(), body.getEmail(), body.getPassword(), body.getDayLimit(), 100L, body.getName());

        user.setEmail(body.getEmail());
        user.setUsername(body.getUsername());
        user.setDayLimit(body.getDayLimit());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setRole(Roles.CUSTOMER);
        user.setTransactionLimit(500L);
        user.setName(body.getName());
        userDTO.save(user);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));
        String token = jwtTokenProvider.createToken(body.getUsername(), userDTO.findByUsername(body.getUsername()).getRole());

        UserLoginEntity userInfo = new UserLoginEntity();
        userInfo.setUsername(user.getUsername());
        userInfo.setRole(user.getRole());

        return new AuthResult(token, userInfo);
    }
}

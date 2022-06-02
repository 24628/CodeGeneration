package io.swagger.service.auth;

import io.swagger.api.exceptions.AuthorizationException;
import io.swagger.api.exceptions.EntityAlreadyExistException;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.enums.Roles;
import io.swagger.helpers.AuthResult;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Entity.DayLimitEntity;
import io.swagger.model.Entity.UserEntity;
import io.swagger.model.RegisterBody;
import io.swagger.model.User;
import io.swagger.repository.IDayLimitDTO;
import io.swagger.repository.IUserDTO;
import io.swagger.service.DayLimitService;
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
    IUserDTO userDTO;

    @Autowired
    IDayLimitDTO dayLimitDTO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Validator validator;

    public AuthResult register(RegisterBody body) throws AuthenticationException, ValidationException, EntityAlreadyExistException
    {
        String token = "";
        UserEntity user = new UserEntity();

        validator.CanCreateUser(body.getUsername(), body.getEmail(), body.getPassword(), body.getDayLimit(), 100L,body.getName());

        user.setEmail(body.getEmail());
        user.setUsername(body.getUsername());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setRole(Roles.CUSTOMER);
        user.setTransaction_limit(500L);
        user.setName(body.getName());
        userDTO.save(user);

        DayLimitEntity dayLimit = new DayLimitEntity();
        dayLimit.setActualLimit(body.getDayLimit());
        dayLimit.setCurrent(0L);
        dayLimit.setUserId(user.getUuid());
        dayLimitDTO.save(dayLimit);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));
        token = jwtTokenProvider.createToken(body.getUsername(), userDTO.findByUsername(body.getUsername()).getRole());

        return new AuthResult(token, user);
    }
}

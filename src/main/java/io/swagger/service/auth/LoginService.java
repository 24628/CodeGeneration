package io.swagger.service.auth;

import io.swagger.api.exceptions.AuthorizationException;
import io.swagger.api.exceptions.InvalidUsernameOrPassword;
import io.swagger.api.exceptions.UserNotFoundException;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Entity.UserEntity;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.core.AuthenticationException;

@Service
public class LoginService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    IUserDTO userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String username, String password)
    {
        String token = "";
        UserEntity user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UserNotFoundException(username);
        }

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidUsernameOrPassword();
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRole());
        } catch (AuthenticationException e) {
            throw new AuthorizationException();
        }
        return token;
    }
}

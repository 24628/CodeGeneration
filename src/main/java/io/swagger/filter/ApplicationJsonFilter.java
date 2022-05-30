package io.swagger.filter;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ApplicationJsonFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String accept = httpServletRequest.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);        // move on to the next filter
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Application format needs to be application/json");
        }
    }

}

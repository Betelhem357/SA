package com.CS590.sample.controller;

import com.CS590.sample.authentication.JWTTokenProvider;
import com.CS590.sample.authentication.UserDetail;
import com.CS590.sample.dto.JWTResponse;
import com.CS590.sample.dto.LoginDTO;
import com.CS590.sample.model.User;
import com.CS590.sample.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTTokenProvider tokenProvider;
    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> authenticateUser( @RequestBody LoginDTO login) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity processRegister(@RequestBody User user) {
        logger.info("User: "+user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return ResponseEntity.ok(this.userService.addUser(user));
    }
}

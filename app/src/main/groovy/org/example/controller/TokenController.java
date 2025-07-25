package org.example.controller;

import org.example.Entities.RefreshToken;
import org.example.Service.JwtService;
import org.example.Service.RefreshTokenService;
import org.example.request.AuthRequestDto;
import org.example.request.RefreshTokenRequestDto;
import org.example.response.JwtResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


    @Controller
    public class TokenController
    {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private RefreshTokenService refreshTokenService;

        @Autowired
        private JwtService jwtService;

        @PostMapping("auth/v1/login")
        public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if(authentication.isAuthenticated()){
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
                return new ResponseEntity(JwtResponseDto.builder()
                        .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                        .token(refreshToken.getToken())
                        .build(), HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("auth/v1/refreshToken")
        public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO){
            return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUserInfo)
                    .map(userInfo -> {
                        String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                        return JwtResponseDto.builder()
                                .accessToken(accessToken)
                                .token(refreshTokenRequestDTO.getToken()).build();
                    }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
        }

    }


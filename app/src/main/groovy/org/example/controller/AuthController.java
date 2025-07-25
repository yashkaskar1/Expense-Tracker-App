package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.Entities.RefreshToken;
import org.example.Service.JwtService;
import org.example.Service.RefreshTokenService;
import org.example.Service.UserDetailsServiceImpl;
import org.example.model.UserInfoDto;
import org.example.response.JwtResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

    @AllArgsConstructor
    @RestController
    public class AuthController
    {

        @Autowired
        private JwtService jwtService;

        @Autowired
        private RefreshTokenService refreshTokenService;

        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        @PostMapping("auth/v1/signup")
        public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto){
            try{
                Boolean isSignUped = userDetailsService.signupUser(userInfoDto);
                if(Boolean.FALSE.equals(isSignUped)){
                    return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
                }
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
                String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
                return new ResponseEntity(JwtResponseDto.builder().accessToken(jwtToken).
                        token(refreshToken.getToken()).build(), HttpStatus.OK);
            }catch (Exception ex){
                return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }


package com.springboot.Configuration;

import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Model.User.Tokens;
import com.springboot.Repository.User.TokenRepository;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
        }
        Tokens tokens = tokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Token not found."));
        tokens.setIsLogOut(true);
        tokens.setLogoutDateTime(Utilities.getCurrentDateTime());
        tokenRepository.save(tokens);
    }
}

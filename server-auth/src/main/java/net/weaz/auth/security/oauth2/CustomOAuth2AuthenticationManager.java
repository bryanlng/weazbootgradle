package net.weaz.auth.security.oauth2;

import net.weaz.auth.security.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2AuthenticationManager extends OAuth2AuthenticationManager {

    private CustomUserDetailsService customUserDetailsService;

    public CustomOAuth2AuthenticationManager(CustomUserDetailsService customUserDetailsService,
                                             @Qualifier("tokenServices") DefaultTokenServices tokenServices) {
        this.customUserDetailsService = customUserDetailsService;
        tokenServices.setAuthenticationManager(this);
        setTokenServices(tokenServices);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication auth = super.authenticate(authentication);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername((String) auth.getPrincipal());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, auth.getCredentials(), auth.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(auth.getDetails());
        return usernamePasswordAuthenticationToken;
    }
}
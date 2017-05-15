package net.weaz.auth.security.remote;

import net.weaz.auth.security.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.rcp.RemoteAuthenticationManager;
import org.springframework.security.authentication.rcp.RemoteAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomRemoteAuthenticationProvider extends RemoteAuthenticationProvider {

    @Autowired
    public CustomRemoteAuthenticationProvider(RemoteAuthenticationManager customRemoteAuthenticationManager, CustomUserDetailsService customUserDetailsService) {
        super();
        setRemoteAuthenticationManager(customRemoteAuthenticationManager);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication attemptedAuthenticationWithAuthorities = super.authenticate(authentication);
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                authentication.getCredentials(),
                attemptedAuthenticationWithAuthorities.getAuthorities());
    }
}
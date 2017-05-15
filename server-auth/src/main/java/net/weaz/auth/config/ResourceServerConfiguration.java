package net.weaz.auth.config;

import net.weaz.auth.security.oauth2.CustomOAuth2AuthenticationManager;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private ResourceServerProperties resource;
    private CustomOAuth2AuthenticationManager customOAuth2AuthenticationManager;

    public ResourceServerConfiguration(ResourceServerProperties resource,
                                       CustomOAuth2AuthenticationManager customOAuth2AuthenticationManager) {
        this.resource = resource;
        this.customOAuth2AuthenticationManager = customOAuth2AuthenticationManager;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(this.resource.getResourceId());
        resources.authenticationManager(customOAuth2AuthenticationManager);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
    }
}
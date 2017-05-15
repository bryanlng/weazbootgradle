package net.weaz.auth.config;

import net.weaz.auth.security.oauth2.CustomClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(AuthorizationServerProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private CustomClientDetailsService customClientDetailsService;
    private AuthorizationServerTokenServices tokenServices;
    private TokenStore tokenStore;
    private OAuth2RequestFactory requestFactory;
    private UserDetailsService userDetailsService;
    private AccessTokenConverter accessTokenConverter;

    @Autowired
    public AuthorizationServerConfiguration(AuthenticationManager authenticationManager,
                                            CustomClientDetailsService customClientDetailsService,
                                            AuthorizationServerTokenServices tokenServices, TokenStore tokenStore,
                                            OAuth2RequestFactory requestFactory, UserDetailsService userDetailsService, AccessTokenConverter accessTokenConverter) {
        super();
        this.authenticationManager = authenticationManager;
        this.customClientDetailsService = customClientDetailsService;
        this.tokenServices = tokenServices;
        this.tokenStore = tokenStore;
        this.requestFactory = requestFactory;
        this.userDetailsService = userDetailsService;
        this.accessTokenConverter = accessTokenConverter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.setClientDetailsService(customClientDetailsService);
        endpoints.tokenServices(tokenServices);
        endpoints.tokenStore(tokenStore);
        endpoints.authenticationManager(authenticationManager);
        endpoints.requestFactory(requestFactory);
        endpoints.userDetailsService(userDetailsService);
        endpoints.accessTokenConverter(accessTokenConverter);
    }

    @Configuration
    static class AuthorizationServerBeanConfiguration {

        private CustomClientDetailsService customClientDetailsService;
        private DataSource dataSource;

        public AuthorizationServerBeanConfiguration(CustomClientDetailsService customClientDetailsService,
                                                    @SuppressWarnings("SpringJavaAutowiringInspection") DataSource dataSource) {
            this.customClientDetailsService = customClientDetailsService;
            this.dataSource = dataSource;
        }

        @Bean
        public OAuth2RequestFactory requestFactory() {
            return new DefaultOAuth2RequestFactory(customClientDetailsService);
        }

        @Bean
        public AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }
    }
}
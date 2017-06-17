package net.weaz.auth.security.oauth2;

import net.weaz.auth.data.models.CustomClient;
import net.weaz.auth.data.repositories.CustomClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CustomClientDetailsService implements ClientDetailsService {

    private CustomClientRepository customClientRepository;

    @Autowired
    public CustomClientDetailsService(CustomClientRepository customClientRepository) {
        this.customClientRepository = customClientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        CustomClient customClient = customClientRepository.findByClientName(clientId);
        if (customClient == null) {
            throw new ClientRegistrationException("Could not find client with clientId: " + clientId);
        }
        return new CustomClientDetails(customClient);
    }

    private static class CustomClientDetails extends CustomClient implements ClientDetails {

        private static final long serialVersionUID = 1600476387755497998L;

        public CustomClientDetails(CustomClient customClient) {
            super(customClient);
        }

        @Override
        public String getClientId() {
            return super.getClientName();
        }

        @Override
        public Set<String> getResourceIds() {
            return null;
        }

        @Override
        public boolean isSecretRequired() {
            return true;
        }

        @Override
        public boolean isScoped() {
            return false;
        }

        @Override
        public Set<String> getScope() {
            return new HashSet<>(Arrays.asList(super.getScopes().replace(" ", "").split(",")));
        }

        @Override
        public Set<String> getAuthorizedGrantTypes() {
            return new HashSet<>(Arrays.asList("authorization_code,token,refresh_token,password".split(",")));
        }

        @Override
        public Set<String> getRegisteredRedirectUri() {
            return null;
        }

        @Override
        public Collection<GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }

        @Override
        public Integer getAccessTokenValiditySeconds() {
            return null;
        }

        @Override
        public Integer getRefreshTokenValiditySeconds() {
            return null;
        }

        @Override
        public boolean isAutoApprove(String scope) {
            return true;
        }

        @Override
        public Map<String, Object> getAdditionalInformation() {
            return null;
        }
    }
}
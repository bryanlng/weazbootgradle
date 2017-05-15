package net.weaz.auth.security.oauth2;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.weaz.auth.data.models.CustomClient;
import net.weaz.auth.data.repositories.CustomClientRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CustomClientDetailsServiceTest {

    private CustomClientDetailsService subject;

    @Mock
    private CustomClientRepository customClientRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    @SuppressFBWarnings(value = "URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD", justification = "MockitoRule usage.")
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        subject = new CustomClientDetailsService(customClientRepository);
    }

    @Test
    public void loadClientByClientId_whenClientRepositoryReturnsAClient_returnsExpectedObject() throws Exception {
        CustomClient customClient = new CustomClient();
        customClient.setId(1L);
        customClient.setClientName("someClient");
        customClient.setClientSecret("clientSecret");
        customClient.setScopes("this, that");

        when(customClientRepository.findByClientName("someClient")).thenReturn(customClient);

        ClientDetails result = subject.loadClientByClientId("someClient");

        assertThat(result.getClientId()).isEqualTo("someClient");
        assertThat(result.getResourceIds()).isNull();
        assertThat(result.isSecretRequired()).isTrue();
        assertThat(result.isScoped()).isFalse();
        assertThat(result.getScope()).containsExactlyInAnyOrder("this", "that");
        assertThat(result.getAuthorizedGrantTypes()).containsExactlyInAnyOrder("authorization_code", "token", "refresh_token", "password");
        assertThat(result.getRegisteredRedirectUri()).isNull();
        assertThat(result.getAuthorities()).contains(new SimpleGrantedAuthority("ROLE_USER"));
        assertThat(result.getAccessTokenValiditySeconds()).isNull();
        assertThat(result.getRefreshTokenValiditySeconds()).isNull();
        assertThat(result.isAutoApprove("")).isTrue();
        assertThat(result.getAdditionalInformation()).isNull();
    }

    @Test
    public void loadClientByClientId_whenClientRepositoryReturnsNoClient_throwsException() throws Exception {
        when(customClientRepository.findByClientName("someClient")).thenReturn(null);

        expectedException.expect(ClientRegistrationException.class);
        expectedException.expectMessage("Could not find client with clientId: someClient");
        subject.loadClientByClientId("someClient");
    }
}
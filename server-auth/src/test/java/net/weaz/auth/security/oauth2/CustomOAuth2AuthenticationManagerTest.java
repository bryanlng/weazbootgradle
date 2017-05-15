package net.weaz.auth.security.oauth2;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.weaz.auth.security.userdetails.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class CustomOAuth2AuthenticationManagerTest {

    @Rule
    @SuppressFBWarnings(value = "URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD", justification = "MockitoRule usage.")
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private CustomOAuth2AuthenticationManager subject;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private DefaultTokenServices defaultTokenServices;
    @Mock
    private OAuth2Authentication previousAuthentication;
    @Mock
    private UserDetails mockUserDetails;
    @Mock
    private Object mockCredentials;
    @Mock
    private Object mockTokenDetails;
    private Collection<GrantedAuthority> mockAuthorities;

    @Before
    public void setUp() throws Exception {
        subject = new CustomOAuth2AuthenticationManager(customUserDetailsService, defaultTokenServices);
        when(previousAuthentication.getPrincipal()).thenReturn("SomePrincipal");
        mockAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        OAuth2Request request = new OAuth2Request(null, null, mockAuthorities, true,
                null, null, null, null, null);

        when(previousAuthentication.getOAuth2Request()).thenReturn(request);
        when(previousAuthentication.getDetails()).thenReturn(mockTokenDetails);
        when(previousAuthentication.getCredentials()).thenReturn(mockCredentials);
        when(previousAuthentication.getAuthorities()).thenReturn(mockAuthorities);
        when(defaultTokenServices.loadAuthentication("SomePrincipal")).thenReturn(previousAuthentication);

        when(customUserDetailsService.loadUserByUsername("SomePrincipal")).thenReturn(mockUserDetails);
    }

    @Test
    public void authenticate_doesRegularAuthenticationFirst_toGetExistingAuthentication_thenLoadsAdditionalDataFromUserService_intoPrincipal_andReturns() throws Exception {
        Authentication result = subject.authenticate(previousAuthentication);

        UsernamePasswordAuthenticationToken expectedToken
                = new UsernamePasswordAuthenticationToken(mockUserDetails, mockCredentials, mockAuthorities);
        expectedToken.setDetails(mockTokenDetails);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedToken);
    }
}
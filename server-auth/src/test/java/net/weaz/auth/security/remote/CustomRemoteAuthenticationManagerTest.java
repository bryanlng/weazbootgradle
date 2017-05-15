package net.weaz.auth.security.remote;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.weaz.auth.data.models.AuthCustomUser;
import net.weaz.auth.data.repositories.CustomUserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomRemoteAuthenticationManagerTest {

    private CustomRemoteAuthenticationManager subject;

    @Mock
    private CustomUserRepository customUserRepository;
    @Captor
    private ArgumentCaptor<AuthCustomUser> authCustomUserCaptor;
    private SampleRemoteUserConfigurationProperties sampleRemoteUserConfigurationProperties;
    private SimpleGrantedAuthority roleUser;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Rule
    @SuppressFBWarnings(value = "URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD", justification = "MockitoRule usage.")
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        sampleRemoteUserConfigurationProperties = new SampleRemoteUserConfigurationProperties();
        sampleRemoteUserConfigurationProperties.setUsername("username");
        sampleRemoteUserConfigurationProperties.setPassword("password");
        sampleRemoteUserConfigurationProperties.setFirstName("SomeFirst");
        sampleRemoteUserConfigurationProperties.setLastName("SomeLast");
        sampleRemoteUserConfigurationProperties.setFavoriteCat("FavoriteCat");
        roleUser = new SimpleGrantedAuthority("ROLE_USER");
        sampleRemoteUserConfigurationProperties.setRole(roleUser);
        subject = new CustomRemoteAuthenticationManager(customUserRepository, sampleRemoteUserConfigurationProperties);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void attemptAuthentication_whenUsernameAndPasswordAreCorrect_andUserIsNotInDatabase_savesPopulatedUser_andReturnsCorrectRole() throws Exception {
        when(customUserRepository.findByUsername("username")).thenReturn(null);

        Collection<? extends GrantedAuthority> result = subject.attemptAuthentication("username", "password");

        assertThat((List<SimpleGrantedAuthority>) result).containsOnly(roleUser);

        verify(customUserRepository).save(authCustomUserCaptor.capture());

        AuthCustomUser expectedSavedUser = new AuthCustomUser();
        expectedSavedUser.setUsername("username");
        expectedSavedUser.setFirstName("SomeFirst");
        expectedSavedUser.setLastName("SomeLast");
        expectedSavedUser.setPassword("password");
        expectedSavedUser.setFavoriteCat("FavoriteCat");

        assertThat(authCustomUserCaptor.getValue()).isEqualToComparingFieldByFieldRecursively(expectedSavedUser);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void attemptAuthentication_whenUsernameAndPasswordAreCorrect_andUserIsInDatabase_doesNotSave_andReturnsCorrectRole() throws Exception {
        when(customUserRepository.findByUsername("username")).thenReturn(new AuthCustomUser());

        Collection<? extends GrantedAuthority> result = subject.attemptAuthentication("username", "password");

        assertThat((List<SimpleGrantedAuthority>) result).containsOnly(roleUser);
        verify(customUserRepository, never()).save(any(AuthCustomUser.class));
    }

    @Test
    public void attemptAuthentication_whenUsernameDoesNotEqualSampleUsername_throwsBadCredentialsException() throws Exception {
        expectedException.expect(BadCredentialsException.class);
        expectedException.expectMessage("Could not authenticate user: wubwub with password: password");
        subject.attemptAuthentication("wubwub", "password");
    }

    @Test
    public void attemptAuthentication_whenPasswordDoesNotEqualSamplePassword_throwsBadCredentialsException() throws Exception {
        expectedException.expect(BadCredentialsException.class);
        expectedException.expectMessage("Could not authenticate user: username with password: notCorrect");
        subject.attemptAuthentication("username", "notCorrect");
    }
}
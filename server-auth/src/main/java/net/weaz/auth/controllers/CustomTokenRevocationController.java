//package net.weaz.auth.controllers;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.ServletException;
//
//@RestController
// todo: this is unused by the application in its example state.  either test this or remove; good example but unnecessary for the purpose of this project.
//public class CustomTokenRevocationController {
//
//    private TokenStore tokenStore;
//
//    @Autowired
//    public CustomTokenRevocationController(TokenStore tokenStore) {
//        this.tokenStore = tokenStore;
//    }
//
//    @RequestMapping(path = "/oauth/revoke", method = {RequestMethod.POST, RequestMethod.DELETE})
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void revoke(Authentication authentication) throws ServletException {
//        String accessToken = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
//        OAuth2AccessToken oauth2AccessToken = tokenStore.readAccessToken(accessToken);
//        tokenStore.removeRefreshToken(oauth2AccessToken.getRefreshToken());
//        tokenStore.removeAccessToken(oauth2AccessToken);
//    }
//}
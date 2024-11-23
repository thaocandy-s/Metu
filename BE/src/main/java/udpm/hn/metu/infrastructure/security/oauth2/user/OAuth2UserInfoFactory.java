package udpm.hn.metu.infrastructure.security.oauth2.user;

import lombok.extern.slf4j.Slf4j;
import udpm.hn.metu.infrastructure.constant.AuthProvider;
import udpm.hn.metu.infrastructure.security.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        log.info("attributes: {}", attributes);
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }

}

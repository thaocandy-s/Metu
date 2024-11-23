package udpm.hn.metu.infrastructure.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import udpm.hn.metu.utils.SecurityUtil;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Slf4j
public class TokenUriResponse {

    private String accessToken;

    private String refreshToken;

    public String getTokenAuthorizationSimple() {
        String tokenObject = "{" + "\"accessToken\":\"" + accessToken + "\",\"refreshToken\":\"" + refreshToken + "\"}";
        return SecurityUtil.encodeBase64(tokenObject);
    }

    public static String getState(
            String accessToken,
            String refreshToken
    ) {
        TokenUriResponse tokenUriResponse = new TokenUriResponse(accessToken, refreshToken);
        return tokenUriResponse.getTokenAuthorizationSimple();
    }

}

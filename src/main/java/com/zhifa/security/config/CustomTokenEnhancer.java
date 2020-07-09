package com.zhifa.security.config;

import com.zhifa.security.dto.SecurityUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;


/**
 * token生成携带的信息 额外携带
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        //SecurityUserDetails user = (SecurityUserDetails) authentication.getUserAuthentication().getPrincipal();
       /* additionalInfo.put("username_", user.getUsername());
        additionalInfo.put("authorities_", user.getAuthorities());*/
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}

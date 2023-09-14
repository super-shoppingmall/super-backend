package com.github.superbackend.service;

import com.github.superbackend.dto.OAuth2TokenResponse;
import com.github.superbackend.util.OAuth2ApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private RestTemplate restTemplate;
    private final OAuth2ApiCaller apiCaller; // OAuth2ApiCaller 추가

    @Autowired
    public CustomOAuth2UserService() {
        this.restTemplate = restTemplate;
        this.apiCaller = new OAuth2ApiCaller(restTemplate);
    }

    // OAuth2 API 호출을 위한 메소드
    private Map<String, Object> callOAuth2Api(OAuth2UserRequest userRequest, String apiUrl) {
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("api_call_error"));
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        Map<String, Object> userInfo = null;

        if ("naver".equals(registrationId)) {
            String apiUrl = "https://openapi.naver.com/v1/nid/me";
            userInfo = apiCaller.callApi(apiUrl, accessToken);
        } else if ("kakao".equals(registrationId)) {
            String apiUrl = "https://kapi.kakao.com/v2/user/me";
            userInfo = apiCaller.callApi(apiUrl, accessToken);
        }

        if (userInfo != null) {
            // 사용자 정보에서 필요한 데이터 추출
            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");

            // 추출한 데이터로 UserDetails 생성
            UserDetails userDetails = User.builder()
                    .username(email) // 이메일을 사용자명으로 설정
                    .password("") // 비밀번호 필드가 필요하지 않은 경우 빈 문자열로 설정
                    .authorities(Collections.emptyList()) // 사용자 권한 설정
                    .build();

            // UserDetails를 포함하는 OAuth2User 생성하여 반환
            return new DefaultOAuth2User(userDetails.getAuthorities(), userInfo, "email");
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("invalid_client_registration", "Unsupported registration ID", null));
    }

    public OAuth2TokenResponse exchangeToken(String code, String state) {
        // 1. 클라이언트 정보 설정
        String clientId = "your_client_id"; // OAuth 2.0 클라이언트 ID
        String clientSecret = "your_client_secret"; // OAuth 2.0 클라이언트 시크릿
        String tokenExchangeUrl = "https://oauth2-provider.com/token"; // OAuth 2.0 토큰 교환 엔드포인트 URL

        // 2. OAuth 2.0 토큰 교환을 위한 요청 준비
        OAuth2AccessToken token = null; // 교환된 액세스 토큰
        OAuth2RefreshToken refreshToken = null; // 교환된 리프레시 토큰

        // 여기에 토큰 교환 요청을 보내고 응답을 처리하는 코드를 작성해야 합니다.
        // 코드와 상태 값을 사용하여 토큰 교환 요청을 만들고, 클라이언트 ID 및 시크릿을 사용하여 인증을 수행합니다.

        // 3. 토큰 교환 성공 시, 교환된 토큰 정보를 OAuth2TokenResponse에 설정
        OAuth2TokenResponse tokenResponse = new OAuth2TokenResponse(token, refreshToken);

        return tokenResponse;
    }
}
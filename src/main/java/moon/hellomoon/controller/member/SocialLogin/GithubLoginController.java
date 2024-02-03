package moon.hellomoon.controller.member.SocialLogin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Controller
@Slf4j
public class GithubLoginController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public GithubLoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/github-login/callback")
    public String githubLoginCallback(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication: {}", authentication);

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("github", authentication.getName());
        if (authorizedClient == null) {
            log.error("OAuth2AuthorizedClient is null for user: {}", authentication.getName());
            return "redirect:/login";
        }

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        log.debug("Access Token: {}", accessToken);

        String userInfo = callGithubUserInfoApi(accessToken);
        log.debug("User Info: {}", userInfo);

        if ("error".equals(userInfo)) {
            return "error";
        }

        model.addAttribute("name", userInfo);
        return "redirect:/";
    }


    private String callGithubUserInfoApi(OAuth2AccessToken accessToken) {
        String userInfoUri = "https://api.github.com/user";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken.getTokenValue());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        RestTemplate restTemplate = new RestTemplate();
        try {
            // 명시적인 제네릭 타입 사용
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    userInfoUri,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> userInfo = response.getBody();
                return processUserInfo(userInfo);
            } else {
                log.error("Failed to call GitHub API or response body is null");
                return "error";
            }
        } catch (RestClientException e) {
            log.error("Error calling GitHub API: ", e);
            return "error";
        }
        }

        private String processUserInfo(Map<String, Object> userInfo) {
            // 사용자 정보 처리 로직
            return (String) userInfo.getOrDefault("name", "Unknown");
        }

}

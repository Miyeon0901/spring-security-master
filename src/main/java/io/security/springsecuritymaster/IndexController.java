package io.security.springsecuritymaster;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public Authentication index(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping("/csrf")
    public String csrf() {
        return "csrf가 적용됨";
    }

    @PostMapping("/ignoreCsrf")
    public String ignoreCsrf() {
        return "csrf가 무시됨";
    }

}

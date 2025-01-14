package io.security.springsecuritymaster;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public Authentication index(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/home")
    public Authentication home(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

}

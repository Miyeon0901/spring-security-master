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

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/db")
    public String db() {
        return "db";
    }

    @GetMapping("/secure")
    public String secure() {
        return "secure";
    }
}

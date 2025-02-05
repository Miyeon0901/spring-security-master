package io.security.springsecuritymaster;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexController {

    private final DataService dataService;

    @GetMapping("/")
    public Authentication index(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/user")
    public String user() {
        return dataService.getUser();
    }

    @GetMapping("/owner")
    public Account owner(String name) {
        return dataService.getOwner(name);
    }

    @GetMapping("/display")
    public String display() {
        return dataService.display();
    }
}

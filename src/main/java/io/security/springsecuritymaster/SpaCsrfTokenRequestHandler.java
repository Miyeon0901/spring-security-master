package io.security.springsecuritymaster;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

public class SpaCsrfTokenRequestHandler extends CsrfTokenRequestAttributeHandler {
    // XorCsrfTokenRequestAttributeHandler는 인코딩 디코딩을 하는 반면, CsrfTokenRequestAttributeHandler 는 하지 않는다.
    // 헤더에는 원본(쿠키로부터 받은 원본 저장) - CsrfTokenRequestAttributeHandler에 위임
    // 다른 곳은 인코딩 - XorCsrfTokenRequestAttributeHandler에 위임.

    private final CsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();

    // CsrfFilter가 처음에 handle을 호출, 선행작업을 xor 핸들러에 맡김.
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> deferredCsrfToken) {
        delegate.handle(request, response, deferredCsrfToken);
    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {

        if (StringUtils.hasText(request.getHeader(csrfToken.getHeaderName()))) { // 인코딩이 안된 경우
            return super.resolveCsrfTokenValue(request, csrfToken);
        }
        return delegate.resolveCsrfTokenValue(request, csrfToken);
    }
}

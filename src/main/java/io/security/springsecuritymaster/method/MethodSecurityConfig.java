package io.security.springsecuritymaster.method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(prePostEnabled = false)
@Configuration
public class MethodSecurityConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor pointCutAdvisor() {
        AspectJExpressionPointcut pattern = new AspectJExpressionPointcut();
        pattern.setExpression("execution(* io.security.springsecuritymaster.DataService.getUser(..))");
        AuthorityAuthorizationManager<MethodInvocation> manager = AuthorityAuthorizationManager.hasRole("USER");
        return new AuthorizationManagerBeforeMethodInterceptor(pattern, manager);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor pointCutAdvisor2() {
        AspectJExpressionPointcut pattern1 = new AspectJExpressionPointcut();
        pattern1.setExpression("execution(* io.security.springsecuritymaster.DataService.getUser(..))");

        AspectJExpressionPointcut pattern2 = new AspectJExpressionPointcut();
        pattern2.setExpression("execution(* io.security.springsecuritymaster.DataService.getOwner(..))");

//        ComposablePointcut composablePointcut = new ComposablePointcut(pattern1, pattern2);
        ComposablePointcut composablePointcut = new ComposablePointcut((Pointcut) pattern1);
        composablePointcut.union((Pointcut) pattern2);

        AuthorityAuthorizationManager<MethodInvocation> manager = AuthorityAuthorizationManager.hasRole("USER");
        return new AuthorizationManagerBeforeMethodInterceptor(composablePointcut, manager);
    }

}

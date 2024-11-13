package com.nhnacademy.jbgw08_043mvcday1.config;

import com.nhnacademy.jbgw08_043mvcday1.interceptor.LoginCheckInterceptor;
import org.apache.logging.log4j.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("message");
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor () {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());

        // 로그인 체크 인터셉터를 추가
        // 체크 제외해야 하는 경로
        // /student/register
        // /student/login
        registry.addInterceptor(new LoginCheckInterceptor())
                .excludePathPatterns("/student/register", "student/login");
    }

}

package com.fastcampus.projectboard.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

/**
 * Thymeleaf Template의 Decoupled 기능을 사용하기 위한 설정
 * application.yaml에서 설정이 가능하게 된다.
 */
@Configuration
public class ThymeleafConfig {
    /**
     * Thymeleaf를 SpringBoot에 넣었을 때 기본 Thymeleaf기능에 decoupled로직을 설정한다.
     *
     * @param defaultTemplateResolver
     * @param thymeleaf3Properties
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver (
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.decoupledLogic());

        return defaultTemplateResolver;
    }

    @ConfigurationProperties("spring.thymeleaf3")
    public record Thymeleaf3Properties(boolean decoupledLogic) {}
}

package com.datasource.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class CharsetConfig implements WebMvcConfigurer {
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                StandardCharsets.UTF_8);
        return converter;
    }
    // 直接替换解析器
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        int index = -1;
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof StringHttpMessageConverter) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            converters.add(responseBodyConverter());
        } else {
            converters.set(index, responseBodyConverter());
        }
    }
}

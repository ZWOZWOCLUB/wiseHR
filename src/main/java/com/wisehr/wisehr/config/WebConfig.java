package com.wisehr.wisehr.config;

import com.wisehr.wisehr.security.auth.filter.HeaderFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${image.add-resource-locations}")
    private String ADD_RESOURCE_LOCATION;

    @Value("${image.add-resource-handler}")
    private String ADD_RESOURCE_HANDLER;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:/public/", "classpath:/",
            "classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(ADD_RESOURCE_HANDLER)
                .addResourceLocations(ADD_RESOURCE_LOCATION);
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean() {

        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public HeaderFilter createHeaderFilter() {

        return new HeaderFilter();
    }
}

package com.carshop.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = System.getProperty("user.dir") + "/uploads/";
        String uploadPathFormatted = "file:///" + uploadPath.replace("\\", "/");

        System.out.println(">>>> Mapeando /uploads/** para: " + uploadPathFormatted);

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPathFormatted);
    }
}


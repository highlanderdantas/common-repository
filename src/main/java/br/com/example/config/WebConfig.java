package br.com.example.config;

import java.lang.reflect.Method;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/",
      "classpath:/resources/", "classpath:/static/", "classpath:/public/"};

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!registry.hasMappingForPattern("/**")) {
      registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
    super.addResourceHandlers(registry);
  }

  @Bean
  public WebMvcRegistrationsAdapter webMvcRegistrationsHandlerMapping() {
    return new WebMvcRegistrationsAdapter() {
      @Override
      public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
          private static final String API_BASE_PATH = "api";

          @Override
          protected void registerHandlerMethod(Object handler, Method method,
              RequestMappingInfo mapping) {
            Class<?> beanType = method.getDeclaringClass();
            if (AnnotationUtils.findAnnotation(beanType, RestApiController.class) != null) {
              PatternsRequestCondition apiPattern = new PatternsRequestCondition(API_BASE_PATH)
                  .combine(mapping.getPatternsCondition());

              mapping = new RequestMappingInfo(mapping.getName(), apiPattern,
                  mapping.getMethodsCondition(), mapping.getParamsCondition(),
                  mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                  mapping.getProducesCondition(), mapping.getCustomCondition());
            }

            super.registerHandlerMethod(handler, method, mapping);
          }

        };
      }
    };
  }

}

package com.identity.configuration.audit;

import org.hibernate.Interceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateInterceptorConfig {
  @Bean
  public Interceptor softDeleteInterceptor() {
    return new SoftDeleteInterceptor();
  }

  @Bean
  public HibernatePropertiesCustomizer interceptorCustomizer(
      Interceptor softDeleteInterceptor) {

    return props -> props.put(
        "hibernate.session_factory.interceptor",
        softDeleteInterceptor);
  }
}

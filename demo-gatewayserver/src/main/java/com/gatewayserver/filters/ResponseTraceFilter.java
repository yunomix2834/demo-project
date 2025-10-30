package com.gatewayserver.filters;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResponseTraceFilter {

    Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    FilterUtility filterUtility;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return ((exchange, chain) -> chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    HttpHeaders headers = exchange.getRequest().getHeaders();
                    String correlationId = filterUtility.getCorrelationId(headers);

                    if(!(exchange.getResponse().getHeaders().containsKey(
                            FilterUtility.CORRELATION_ID))) {
                        logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);

                        exchange.getResponse().getHeaders().add(
                                FilterUtility.CORRELATION_ID, correlationId);
                    }
                })));
    }

}

package com.gatewayserver.filters;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static java.util.UUID.randomUUID;


@Order(1)
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestTraceFilter implements GlobalFilter {

    Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        if (isCorrelationIdPresent(requestHeaders)) {
            logger.debug("yunomix2834-correlation-id found in RequestTraceFilter: {}",
                        filterUtility.getCorrelationId(requestHeaders)
                    );
        } else {
            String correlationId = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationId);

            logger.debug("yunomix2834-correlation-id generated in RequestTraceFilter: {}",
                    correlationId);
        }

        return chain.filter(exchange);
    }

    boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtility.getCorrelationId(requestHeaders) != null;
    }

    String generateCorrelationId() {
        return randomUUID().toString();
    }
}

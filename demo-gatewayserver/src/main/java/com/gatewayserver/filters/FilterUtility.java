package com.gatewayserver.filters;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilterUtility {

    public static final String CORRELATION_ID = "yunomix2834-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null;
        }
    }

    public ServerWebExchange setRequestHeader(
            ServerWebExchange serverWebExchange,
            String name,
            String value) {
        return serverWebExchange.mutate()
                .request(serverWebExchange.getRequest()
                        .mutate()
                        .header(name, value)
                        .build())
                .build();
    }

    public ServerWebExchange setCorrelationId(
            ServerWebExchange serverWebExchange,
            String correlationId) {
        return this.setRequestHeader(
                serverWebExchange,
                CORRELATION_ID,
                correlationId
        );
    }
}

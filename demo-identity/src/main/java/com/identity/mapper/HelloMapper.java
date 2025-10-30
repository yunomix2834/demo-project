package com.identity.mapper;

import com.identity.dto.request.HelloRequest;
import com.identity.dto.response.HelloResponse;
import com.identity.entity.Hello;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface HelloMapper {

    Hello toHelloFromHelloRequest(HelloRequest helloRequest);

    HelloResponse toHelloResponseFromHello(Hello hello);
}

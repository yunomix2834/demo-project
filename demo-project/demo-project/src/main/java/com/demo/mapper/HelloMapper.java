package com.demo.mapper;

import com.demo.dto.request.HelloRequest;
import com.demo.dto.response.HelloResponse;
import com.demo.entity.Hello;
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

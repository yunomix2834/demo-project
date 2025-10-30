package com.demo.controller;

import com.demo.service.IHelloService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yunomi Xavia
 */

@Builder
@RestController
@RequestMapping(
        path = "/api",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(
        name = "CRUD REST APIs for Hello",
        description = "CRUD REST APIs to CREATE, GET, UPDATE, PATCH"
)
@Validated
public class HelloController {

    IHelloService helloService;
    Logger logger = LoggerFactory.getLogger(HelloController.class);

//    /**
//     * Fetch Java Version
//     */
//    String fetchJavaVersion();
//
//    /**
//     *
//     * Fetch Java Home
//     */
//    String fetchJavaHome();
//
//    /**
//     * Hello World
//     */
//    String helloWorld();
//
//    /**
//     * Get All Hello World With Page
//     */
//    PageResponse<HelloResponse> getAllHelloWorld(
//            int page, int size,
//            SortField sortBy, boolean asc
//    );
//
//    /**
//     * Get Hello World By Id
//     */
//    HelloResponse getHelloWorldById(String id);
//
//    /**
//     *
//     * Create HelloWorld
//     * @param helloRequest
//     */
//    void createHelloWorld(HelloRequest helloRequest);
}

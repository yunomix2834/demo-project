package com.identity.service;

import com.identity.constant.sort.SortField;
import com.identity.dto.PageResponse;
import com.identity.dto.request.HelloRequest;
import com.identity.dto.response.HelloResponse;

public interface IHelloService {

    /**
     * Fetch Java Version
     */
    String fetchJavaVersion();

    /**
     *
     * Fetch Java Home
     */
    String fetchJavaHome();

    /**
     * Hello World
     */
    String helloWorld();

    /**
     * Get All Hello World With Page
     */
    PageResponse<HelloResponse> getAllHelloWorld(
            int page, int size,
            SortField sortBy, boolean asc
    );

    /**
     * Get Hello World By Id
     */
    HelloResponse getHelloWorldById(String id);

    /**
     *
     * Create HelloWorld
     * @param helloRequest
     */
    void createHelloWorld(HelloRequest helloRequest);
}

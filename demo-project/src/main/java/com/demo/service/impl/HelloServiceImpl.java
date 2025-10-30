package com.demo.service.impl;

import com.demo.constant.sort.SortField;
import com.demo.dto.PageResponse;
import com.demo.dto.request.HelloRequest;
import com.demo.dto.response.HelloResponse;
import com.demo.entity.Hello;
import com.demo.helper.PageResponseHelper;
import com.demo.helper.SortHelper;
import com.demo.mapper.HelloMapper;
import com.demo.repository.HelloRepository;
import com.demo.service.IHelloService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HelloServiceImpl implements IHelloService {

    HelloRepository helloRepository;
    HelloMapper helloMapper;

    Environment env;

    /**
     * @return java version
     */
    @Override
    public String fetchJavaVersion() {
        String javaVersion = env.getProperty("java.version");
        return javaVersion;
    }

    /**
     * @return java home
     */
    @Override
    public String fetchJavaHome() {
        String javaHome = env.getProperty("java.home");
        return javaHome;
    }

    /**
     * @return the new account details
     */
    @Override
    public String helloWorld() {
        return "Hello World From Demo 2!";
    }

    /**
     *
     * @param page
     * @param size
     * @param sortBy
     * @param asc
     * @return list of hello world
     */
    @Override
    public PageResponse<HelloResponse> getAllHelloWorld(
            int page, int size,
            SortField sortBy, boolean asc) {

        Pageable pageable = PageRequest.of(
                page - 1, size,
                SortHelper.build(sortBy, asc)
        );

        Page<HelloResponse> pageData =  helloRepository
                .findAll(pageable)
                .map(helloMapper::toHelloResponseFromHello);

        return PageResponseHelper.toPageResponse(pageData, page);
    }

    /**
     *
     * @param id
     * @return hello world with existing id
     */
    @Override
    public HelloResponse getHelloWorldById(String id) {
        Hello hello = helloRepository
                .findById(id)
                .orElse(null);

        return helloMapper.toHelloResponseFromHello(hello);
    }

    /**
     * @param helloRequest
     * @return void
     */
    @Override
    public void createHelloWorld(HelloRequest helloRequest) {
        Hello hello = helloMapper.toHelloFromHelloRequest(helloRequest);
        helloRepository.save(hello);
    }
}

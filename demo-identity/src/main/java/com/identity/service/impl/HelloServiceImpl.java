package com.identity.service.impl;

import com.identity.constant.sort.SortField;
import com.identity.dto.PageResponse;
import com.identity.dto.request.HelloRequest;
import com.identity.dto.response.HelloResponse;
import com.identity.entity.Hello;
import com.identity.helper.PageResponseHelper;
import com.identity.helper.SortHelper;
import com.identity.mapper.HelloMapper;
import com.identity.repository.HelloRepository;
import com.identity.service.IHelloService;
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
        return "Hello World From Demo 1!";
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

package com.identity.service.impl;

import com.identity.mapper.UserMapper;
import com.identity.repository.UserRepository;
import com.identity.service.IHelloService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HelloServiceImpl implements IHelloService {

    UserRepository userRepository;
    UserMapper userMapper;

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
}

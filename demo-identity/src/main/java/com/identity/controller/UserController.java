package com.identity.controller;

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
        name = "CRUD REST APIs for User",
        description = "CRUD REST APIs to CREATE, GET, UPDATE, PATCH"
)
@Validated
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
}

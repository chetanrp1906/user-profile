package com.chetan.userprofile.user;

import com.chetan.userprofile.TestSpecs;
import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.error.UserErrorCode;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

class UserControllerTests implements TestSpecs {

    @Test
    @DisplayName("User should be created when request is valid")
    void shouldCreateUserWhenRequestIsValid() {
        UserDTO userDTO = createUserCreateRequestBody();

        given()
                .body(userDTO, ObjectMapperType.JACKSON_2)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest(name = "{index} User should not be created if first name is invalid - (FirstName={0})")
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void shouldNotCreateUserWhenFirstNameIsMissing(String firstName) {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setFirstName(firstName);

        given()
                .body(userDTO, ObjectMapperType.JACKSON_2)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .post("/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code[0]", equalTo(UserErrorCode.FIRST_NAME_REQUIRED.getCode()));
    }

    @Test
    @DisplayName("Create user API should return multiple errors when multiple fields are invalid")
    void shouldReturnMultipleErrors() {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setFirstName("");
        userDTO.setPassword("");

        given()
                .body(userDTO, ObjectMapperType.JACKSON_2)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .post("/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("size()", is(2));
    }

    private UserDTO createUserCreateRequestBody() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(randomAlphabeticString());
        userDTO.setLastName(randomAlphabeticString());
        userDTO.setEmail(userDTO.getFirstName() + "." + userDTO.getLastName() + "@test.com");
        userDTO.setPassword("password1");
        return userDTO;
    }
}

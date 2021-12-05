package com.chetan.userprofile.user;

import com.chetan.userprofile.TestSpecs;
import com.chetan.userprofile.error.dto.ErrorDTO;
import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.error.UserErrorCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.equalTo;

class CreateUserAPITests implements TestSpecs {

    @Test
    @DisplayName("User should be created when request is valid")
    void shouldCreateUserWhenRequestIsValid() {
        UserDTO userDTO = createUserCreateRequestBody();

        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.CREATED.value());
    }


    @ParameterizedTest(name = "{index} User should not be created if first name is invalid - (FirstName={0})")
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void shouldNotCreateUserWhenFirstNameIsMissing(String firstName) {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setFirstName(firstName);

        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code[0]", equalTo(UserErrorCode.FIRST_NAME_REQUIRED.getCode()));
    }

    @ParameterizedTest(name = "{index} User should not be created if password is empty - (password={0})")
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void shouldNotCreateUserWhenPasswordIsMissing(String password) {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setPassword(password);

        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code[0]", equalTo(UserErrorCode.PASSWORD_REQUIRED.getCode()));
    }

    @ParameterizedTest(name = "{index} User should not be created if password is invalid - (password={0})")
    @ValueSource(strings = {"pass", "pass12", "passwordabc"})
    void shouldNotCreateUserWhenPasswordIsNotValid(String password) {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setPassword(password);

        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code[0]", equalTo(UserErrorCode.INVALID_PASSWORD.getCode()));
    }

    @ParameterizedTest(name = "{index} User should not be created if email is empty - (email={0})")
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void shouldNotCreateUserWhenEmailIsMissing(String email) {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setEmail(email);

        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code[0]", equalTo(UserErrorCode.EMAIL_REQUIRED.getCode()));
    }

    @ParameterizedTest(name = "{index} User should not be created if email is invalid - (email={0})")
    @ValueSource(strings = {"test", "test@", "test@.", "test@user.", "test."})
    void shouldNotCreateUserWhenEmailIsNotValid(String email) {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setEmail(email);

        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code[0]", equalTo(UserErrorCode.INVALID_EMAIL.getCode()));
    }

    @Test
    void shouldNotCreateUserWhenEmailAlreadyInUse() {
        UserDTO userDTO = createUserCreateRequestBody();
        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.CREATED.value());

        UserDTO newUser = createUserCreateRequestBody();
        newUser.setEmail(userDTO.getEmail());

        buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code[0]", equalTo(UserErrorCode.EMAIL_IN_USE.getCode()));

    }

    @Test
    @DisplayName("Create user API should return multiple errors when multiple fields are invalid")
    void shouldReturnMultipleErrors() {
        UserDTO userDTO = createUserCreateRequestBody();
        userDTO.setFirstName("");
        userDTO.setPassword("");
        userDTO.setEmail("");

        List<ErrorDTO> errors = buildCreateUserRequest(userDTO)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(new TypeRef<List<ErrorDTO>>() {
                });

        assertThat(errors.size()).isEqualTo(3);
        assertThat(errors).allMatch(errorDTO ->
                errorDTO.getCode() == UserErrorCode.FIRST_NAME_REQUIRED.getCode() ||
                        errorDTO.getCode() == UserErrorCode.PASSWORD_REQUIRED.getCode() ||
                        errorDTO.getCode() == UserErrorCode.EMAIL_REQUIRED.getCode()
        );
    }

    private ValidatableResponse buildCreateUserRequest(UserDTO userDTO) {
        return given()
                .body(userDTO, ObjectMapperType.JACKSON_2)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .post("/users")
                .then();
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

package com.efborchardt.productfeedback.infrastructure.interfaces.rest.routes.user;

import com.efborchardt.productfeedback.application.usecase.user.changepassword.ChangePasswordRequestDTO;
import com.efborchardt.productfeedback.application.usecase.user.changepassword.ChangePasswordResponseDTO;
import com.efborchardt.productfeedback.application.usecase.user.changepassword.ChangePasswordUseCase;
import com.efborchardt.productfeedback.application.usecase.user.create.CreateUserRequestDTO;
import com.efborchardt.productfeedback.application.usecase.user.create.CreateUserResponseDTO;
import com.efborchardt.productfeedback.application.usecase.user.create.CreateUserUseCase;
import com.efborchardt.productfeedback.application.usecase.user.find.FindUserResponseDTO;
import com.efborchardt.productfeedback.application.usecase.user.find.FindUserUseCase;
import com.efborchardt.productfeedback.application.usecase.user.list.ListAllUsersUseCase;
import com.efborchardt.productfeedback.application.usecase.user.list.UserListResponseDTO;
import com.efborchardt.productfeedback.application.usecase.user.list.UserResponseDTO;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.domain.user.repository.UserRepository;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.AuthorizationRequestFilter;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.TestSecurityConfigs;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfigs.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TokenService tokenService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private ListAllUsersUseCase listAllUsersUseCase;

    @MockBean
    private ChangePasswordUseCase changePasswordUseCase;

    @MockBean
    private FindUserUseCase findUserUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUserTest() throws Exception {
        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO();
        requestDTO.setUsername("user");
        CreateUserResponseDTO responseDTO = new CreateUserResponseDTO(
                UUID.randomUUID().toString(),
                "user",
                "password",
                "email@email.com"
        );

        given(createUserUseCase.execute(any())).willReturn(responseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAllUsersTest() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                UUID.randomUUID(),
                "username",
                "email@email.com",
                "password"
        );
        UserListResponseDTO responseDTO = new UserListResponseDTO(List.of(userResponseDTO));

        given(listAllUsersUseCase.execute()).willReturn(responseDTO);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findByIdTest() throws Exception {
        FindUserResponseDTO responseDTO = new FindUserResponseDTO(
                UUID.randomUUID(),
                "username",
                "email@email.com",
                "password"
        );

        given(findUserUseCase.execute(eq(responseDTO.getId()))).willReturn(responseDTO);

        mockMvc.perform(get("/users/{id}", responseDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void changePasswordTest() throws Exception {
        UUID userId = UUID.randomUUID();
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO();
        requestDTO.setNewPassword("new_password");
        ChangePasswordResponseDTO responseDTO = new ChangePasswordResponseDTO();

        given(changePasswordUseCase.execute(eq(userId), anyString())).willReturn(responseDTO);

        mockMvc.perform(patch("/users/{userId}/change-password", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Password updated successfully"));
    }
}
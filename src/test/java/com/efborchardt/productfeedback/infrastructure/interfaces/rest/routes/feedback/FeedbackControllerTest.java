package com.efborchardt.productfeedback.infrastructure.interfaces.rest.routes.feedback;

import com.efborchardt.productfeedback.application.usecase.feedback.changecontent.ChangeFeedbackContentRequestDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.changecontent.ChangeFeedbackContentResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.changecontent.ChangeFeedbackContentUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.create.CreateFeedbackRequestDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.create.CreateFeedbackResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.create.CreateFeedbackUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.find.FindFeedbackResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.find.FindFeedbackUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.list.FeedbackResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.list.ListFeedbackResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.list.byproduct.ListFeedbackByProductUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.list.byuser.ListFeedbackByUserUseCase;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.TestSecurityConfigs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfigs.class)
@ActiveProfiles("test")
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListFeedbackByProductUseCase listFeedbackByProductUseCase;

    @MockBean
    private ListFeedbackByUserUseCase listFeedbackByUserUseCase;

    @MockBean
    private FindFeedbackUseCase findFeedbackUseCase;

    @MockBean
    private CreateFeedbackUseCase createFeedbackUseCase;

    @MockBean
    private ChangeFeedbackContentUseCase changeFeedbackContentUseCase;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final User user = new User(
            UUID.randomUUID(),
            "test",
            "email@email.com",
            "password",
            UserRole.USER
    );
    private final Product product = new Product(
            UUID.randomUUID(),
            "Product name",
            "Product description",
            BigDecimal.valueOf(10.96),
            user
    );

    @Test
    @WithMockUser
    void findById() throws Exception {
        UUID feedbackId = UUID.randomUUID();
        FindFeedbackResponseDTO responseDTO = new FindFeedbackResponseDTO(
                feedbackId,
                "Content",
                LocalDateTime.now(),
                user,
                product
        );

        given(findFeedbackUseCase.execute(eq(feedbackId))).willReturn(responseDTO);

        mockMvc.perform(get("/feedbacks/{id}", feedbackId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(feedbackId.toString()));
    }

    @Test
    @WithMockUser
    void getFeedbacksByProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ListFeedbackResponseDTO responseDTO = new ListFeedbackResponseDTO(List.of(
                new FeedbackResponseDTO(UUID.randomUUID(), "Content", LocalDateTime.now(), user.getUsername(), product.getName())
        ));

        given(listFeedbackByProductUseCase.execute(productId)).willReturn(responseDTO);

        mockMvc.perform(get("/feedbacks/by-product/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feedbacks.[0].id").value(responseDTO.getFeedbacks().get(0).getId().toString()))
                .andExpect(jsonPath("$.feedbacks.[0].content").value(responseDTO.getFeedbacks().get(0).getContent()))
                .andExpect(jsonPath("$.feedbacks.[0].submissionDate").exists())
                .andExpect(jsonPath("$.feedbacks.[0].submittedBy").value(responseDTO.getFeedbacks().get(0).getSubmittedBy()))
                .andExpect(jsonPath("$.feedbacks.[0].productName").value(responseDTO.getFeedbacks().get(0).getProductName()))
        ;
    }

    @Test
    @WithMockUser
    void createFeedback() throws Exception {
        CreateFeedbackRequestDTO requestDTO = new CreateFeedbackRequestDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "content"
        );
        CreateFeedbackResponseDTO responseDTO = new CreateFeedbackResponseDTO(
                UUID.randomUUID(),
                "content",
                LocalDateTime.now(),
                "User name",
                "Product name"
        );

        given(createFeedbackUseCase.execute(any())).willReturn(responseDTO);

        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void changeContent() throws Exception {
        UUID feedbackId = UUID.randomUUID();
        ChangeFeedbackContentRequestDTO requestDTO = new ChangeFeedbackContentRequestDTO();
        requestDTO.setContent("updated content");
        ChangeFeedbackContentResponseDTO responseDTO = new ChangeFeedbackContentResponseDTO(
                "Product name",
                "updated content"
        );

        given(changeFeedbackContentUseCase.execute(eq(feedbackId), any(ChangeFeedbackContentRequestDTO.class))).willReturn(responseDTO);

        mockMvc.perform(patch("/feedbacks/{feedbackId}/change-content", feedbackId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.updatedContent").exists());
    }
}
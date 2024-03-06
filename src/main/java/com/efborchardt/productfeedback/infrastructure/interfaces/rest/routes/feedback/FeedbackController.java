package com.efborchardt.productfeedback.infrastructure.interfaces.rest.routes.feedback;

import com.efborchardt.productfeedback.application.usecase.feedback.changecontent.ChangeFeedbackContentRequestDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.changecontent.ChangeFeedbackContentResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.changecontent.ChangeFeedbackContentUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.create.CreateFeedbackRequestDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.create.CreateFeedbackResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.create.CreateFeedbackUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.find.FindFeedbackResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.find.FindFeedbackUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.list.ListFeedbackResponseDTO;
import com.efborchardt.productfeedback.application.usecase.feedback.list.byproduct.ListFeedbackByProductUseCase;
import com.efborchardt.productfeedback.application.usecase.feedback.list.byuser.ListFeedbackByUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final ListFeedbackByProductUseCase listFeedbackByProductUseCase;
    private final ListFeedbackByUserUseCase listFeedbackByUserUseCase;
    private final FindFeedbackUseCase findFeedbackUseCase;
    private final CreateFeedbackUseCase createFeedbackUseCase;
    private final ChangeFeedbackContentUseCase changeFeedbackContentUseCase;

    @Autowired
    public FeedbackController(ListFeedbackByProductUseCase listFeedbackByProductUseCase,
                              ListFeedbackByUserUseCase listFeedbackByUserUseCase,
                              FindFeedbackUseCase findFeedbackUseCase, CreateFeedbackUseCase createFeedbackUseCase, ChangeFeedbackContentUseCase changeFeedbackContentUseCase) {
        this.listFeedbackByProductUseCase = listFeedbackByProductUseCase;
        this.listFeedbackByUserUseCase = listFeedbackByUserUseCase;
        this.findFeedbackUseCase = findFeedbackUseCase;
        this.createFeedbackUseCase = createFeedbackUseCase;
        this.changeFeedbackContentUseCase = changeFeedbackContentUseCase;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindFeedbackResponseDTO> findById(@PathVariable UUID id) {
        final FindFeedbackResponseDTO response = this.findFeedbackUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(
            value = "/by-product/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ListFeedbackResponseDTO> getFeedbacksByProduct(@PathVariable UUID productId) {
        final ListFeedbackResponseDTO response = this.listFeedbackByProductUseCase.execute(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(
            value = "/by-user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ListFeedbackResponseDTO> getFeedbacksByUser(@PathVariable UUID userId) {
        final ListFeedbackResponseDTO response = this.listFeedbackByUserUseCase.execute(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CreateFeedbackResponseDTO> create(@RequestBody CreateFeedbackRequestDTO request) {
        final CreateFeedbackResponseDTO response = this.createFeedbackUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(
            value = "/{feedbackId}/change-content",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChangeFeedbackContentResponseDTO> changeContent(@PathVariable UUID feedbackId,
                                                                          @RequestBody ChangeFeedbackContentRequestDTO request) {
        final ChangeFeedbackContentResponseDTO response = this.changeFeedbackContentUseCase.execute(feedbackId, request);
        return ResponseEntity.ok(response);
    }
}

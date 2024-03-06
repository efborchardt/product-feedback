package com.efborchardt.productfeedback.application.usecase.common;

public interface DefaultUseCase<Input, Output> {
    Output execute(Input input);
}

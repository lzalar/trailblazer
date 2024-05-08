package com.lzalar.raceproducer.domain.exception;

import java.util.List;


public record ApiError(int code, String message, List<ViolationError> violationErrors) {
}

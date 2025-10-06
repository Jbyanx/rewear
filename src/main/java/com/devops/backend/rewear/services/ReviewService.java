package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.SaveReview;
import com.devops.backend.rewear.dtos.response.GetReview;
import jakarta.validation.Valid;

public interface ReviewService {
    GetReview save(Long exchangeId, @Valid SaveReview saveReview);
}

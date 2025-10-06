package com.devops.backend.rewear.controllers;

import com.devops.backend.rewear.dtos.request.SaveReview;
import com.devops.backend.rewear.dtos.response.GetReview;
import com.devops.backend.rewear.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewRepository) {
        this.reviewService = reviewRepository;
    }

    @PostMapping("/{exchangeId}/review")
    public ResponseEntity<GetReview> save(@PathVariable Long exchangeId, @RequestBody @Valid SaveReview saveReview) {
        return ResponseEntity.ok().body(reviewService.save(exchangeId, saveReview));
    }
}

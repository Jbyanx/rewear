package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.SaveReview;
import com.devops.backend.rewear.dtos.response.GetReview;
import com.devops.backend.rewear.entities.Exchange;
import com.devops.backend.rewear.entities.Review;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.enums.ExchangeStatus;
import com.devops.backend.rewear.exceptions.ExchangeNotFoundException;
import com.devops.backend.rewear.exceptions.InvalidReviewException;
import com.devops.backend.rewear.exceptions.PermissionDeniedException;
import com.devops.backend.rewear.mappers.ReviewMapper;
import com.devops.backend.rewear.repositories.ExchangeRepository;
import com.devops.backend.rewear.repositories.ReviewRepository;
import com.devops.backend.rewear.services.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CurrentUserService currentUserService;
    private final ExchangeRepository exchangeRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, CurrentUserService currentUserService, ExchangeRepository exchangeRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.currentUserService = currentUserService;
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    @Transactional
    public GetReview save(Long exchangeId, SaveReview saveReview) {
        // Usuario actual
        User currentUser = currentUserService.getAuthenticatedUser();

        // Obtener exchange
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(() -> new ExchangeNotFoundException("Exchange not found"));

        // Validacion de intercambio completado
        if(!exchange.getStatus().equals(ExchangeStatus.COMPLETED)){
            throw new InvalidReviewException("No puedes calificar un intercambio que no ha sido completado");
        }

        // Validar participación
        if (!(exchange.getOwner().getId().equals(currentUser.getId())
                || exchange.getRequester().getId().equals(currentUser.getId()))) {
            throw new PermissionDeniedException("Solo los participantes pueden realizar reviews");
        }

        //validar que no haya calificado antes
        boolean alreadyReviewed = reviewRepository.existsByExchangeAndReviewer(exchange, currentUser);
        if (alreadyReviewed) {
            throw new InvalidReviewException("Ya realizaste una review para este intercambio");
        }

        // Determinar reviewer y reviewedUser
        User reviewer;
        User reviewedUser;

        if (exchange.getOwner().getId().equals(currentUser.getId())) { //si el owner es el principal
            reviewer = currentUser;
            reviewedUser = exchange.getRequester(); // el requester es el otro
        } else { //si el requester es el principal
            reviewer = currentUser;
            reviewedUser = exchange.getOwner(); //el owner es el otro
        }

        //Crear Review
        Review review = Review.builder()
                .exchange(exchange)
                .reviewer(reviewer)
                .reviewedUser(reviewedUser)
                .rating(saveReview.rating())
                .comment(saveReview.comment())
                .createdAt(LocalDateTime.now()) //posiblemente da error
                .build();

        // Actualizar rating promedio
        int newTotalRatings = reviewedUser.getTotalRatings() + 1;
        BigDecimal totalScore = reviewedUser.getRating()
                .multiply(BigDecimal.valueOf(reviewedUser.getTotalRatings()))
                .add(BigDecimal.valueOf(saveReview.rating()));
        BigDecimal newAverage = totalScore.divide(BigDecimal.valueOf(newTotalRatings), 2, RoundingMode.HALF_UP);

        reviewedUser.setTotalRatings(newTotalRatings);
        reviewedUser.setRating(newAverage);

        // Guardar review y devolver DTO
        return reviewMapper.toGetReview(reviewRepository.save(review));
    }
}

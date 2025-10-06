package com.devops.backend.rewear.mappers;

import com.devops.backend.rewear.dtos.request.SaveReview;
import com.devops.backend.rewear.dtos.response.GetReview;
import com.devops.backend.rewear.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewMapper {

    // SaveReview -> Review
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exchange", ignore = true) // se asigna en el service
    @Mapping(target = "reviewer", ignore = true) // se asigna en el service
    @Mapping(target = "reviewedUser", ignore = true) // se asigna en el service
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Review toReview(SaveReview saveReview);

    // Review -> GetReview
    @Mapping(source = "reviewer", target = "reviewer")
    @Mapping(source = "reviewedUser", target = "reviewed")
    GetReview toGetReview(Review review);

    // List mapping
    List<GetReview> toGetReviews(List<Review> reviews);
}

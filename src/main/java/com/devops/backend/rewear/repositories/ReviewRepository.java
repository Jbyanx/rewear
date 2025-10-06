package com.devops.backend.rewear.repositories;

import com.devops.backend.rewear.entities.Exchange;
import com.devops.backend.rewear.entities.Review;
import com.devops.backend.rewear.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByExchangeAndReviewer(Exchange exchange, User currentUser);
}

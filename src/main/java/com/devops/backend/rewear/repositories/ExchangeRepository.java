package com.devops.backend.rewear.repositories;

import com.devops.backend.rewear.entities.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}

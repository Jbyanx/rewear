package com.devops.backend.rewear.repositories;

import com.devops.backend.rewear.dtos.response.GetWear;
import com.devops.backend.rewear.entities.Wear;
import com.devops.backend.rewear.entities.enums.WearStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WearRepository extends JpaRepository<Wear,Long> {
    List<Wear> getWearsByActiveTrueAndStatus(WearStatus status);
}

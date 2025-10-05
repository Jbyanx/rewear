package com.devops.backend.rewear.repositories;

import com.devops.backend.rewear.dtos.response.GetWear;
import com.devops.backend.rewear.entities.Wear;
import com.devops.backend.rewear.entities.enums.WearStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WearRepository extends JpaRepository<Wear,Long>, JpaSpecificationExecutor<Wear> {
    List<Wear> getWearsByActiveTrueAndStatus(WearStatus status);
}

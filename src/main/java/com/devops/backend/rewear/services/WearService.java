package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.SaveWear;
import com.devops.backend.rewear.dtos.request.UpdateWear;
import com.devops.backend.rewear.dtos.request.WearFilter;
import com.devops.backend.rewear.dtos.response.GetWear;
import com.devops.backend.rewear.entities.enums.WearStatus;
import jakarta.validation.Valid;

import java.util.List;

public interface WearService {
    GetWear createWear(SaveWear saveWear);

    List<GetWear> getAvailableWears(WearFilter filter);

    GetWear updateStatus(Long id, boolean active);

    GetWear updateWear(Long id, @Valid UpdateWear updateWear);
}

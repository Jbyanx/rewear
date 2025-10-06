package com.devops.backend.rewear.services;

import com.devops.backend.rewear.dtos.request.SaveExchange;
import com.devops.backend.rewear.dtos.response.GetExchange;

import java.util.List;

public interface ExchangeService {
    GetExchange save(SaveExchange saveExchange);

    GetExchange acceptExchange(Long id);

    GetExchange confirmExchange(Long id);

    GetExchange rejectExchange(Long id);

    GetExchange cancelExchange(Long id);

    List<GetExchange> getAllExchanges();
}

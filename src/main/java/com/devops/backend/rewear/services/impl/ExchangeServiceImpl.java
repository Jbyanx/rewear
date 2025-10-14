package com.devops.backend.rewear.services.impl;

import com.devops.backend.rewear.dtos.request.SaveExchange;
import com.devops.backend.rewear.dtos.response.GetExchange;
import com.devops.backend.rewear.entities.Exchange;
import com.devops.backend.rewear.entities.User;
import com.devops.backend.rewear.entities.Wear;
import com.devops.backend.rewear.entities.enums.ExchangeStatus;
import com.devops.backend.rewear.entities.enums.WearStatus;
import com.devops.backend.rewear.exceptions.ExchangeNotFoundException;
import com.devops.backend.rewear.exceptions.InvalidExchangeException;
import com.devops.backend.rewear.exceptions.WearNotFoundException;
import com.devops.backend.rewear.mappers.ExchangeMapper;
import com.devops.backend.rewear.repositories.ExchangeRepository;
import com.devops.backend.rewear.repositories.UserRepository;
import com.devops.backend.rewear.repositories.WearRepository;
import com.devops.backend.rewear.services.ExchangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final WearRepository wearRepository;
    private final ExchangeMapper exchangeMapper;

    public ExchangeServiceImpl(ExchangeRepository exchangeRepository, UserRepository userRepository, CurrentUserService currentUserService, WearRepository wearRepository, ExchangeMapper exchangeMapper) {
        this.exchangeRepository = exchangeRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
        this.wearRepository = wearRepository;
        this.exchangeMapper = exchangeMapper;
    }

    @Override
    @Transactional
    public GetExchange save(SaveExchange saveExchange) {
        Long requesterId = currentUserService.getAuthenticatedUser().getId();

        // 1. Obtener la prenda ofrecida (debe ser del usuario autenticado)
        Wear offeredWear = wearRepository.findById(saveExchange.offeredWearId())
                .orElseThrow(() -> new WearNotFoundException("La prenda ofrecida no existe"));
        if (!offeredWear.getOwner().getId().equals(requesterId)) {
            throw new InvalidExchangeException("No puedes ofrecer una prenda que no te pertenece");
        }

        // 2. Obtener la prenda solicitada (debe ser de otro usuario)
        Wear requestedWear = wearRepository.findById(saveExchange.requestedWearId())
                .orElseThrow(() -> new WearNotFoundException("La prenda solicitada no existe"));
        if (requestedWear.getOwner().getId().equals(requesterId)) {
            throw new InvalidExchangeException("No puedes solicitar una de tus propias prendas");
        }

        //3. Crear el intercambio
        Exchange exchange = Exchange.builder()
                .requester(userRepository.getReferenceById(requesterId))
                .owner(requestedWear.getOwner()) //el owner el el propietario de la prenda que se solicita
                .offeredWear(offeredWear)
                .requestedWear(requestedWear)
                .status(ExchangeStatus.PENDING)
                .build();

        Exchange saved = exchangeRepository.save(exchange);

        return exchangeMapper.toGetExchange(saved);
    }

    @Override
    @Transactional
    public GetExchange acceptExchange(Long id) {
        User user = currentUserService.getAuthenticatedUser();
        Exchange exchange = exchangeRepository.findById(id)
                .orElseThrow(() -> new ExchangeNotFoundException("Error al cambiar el estado del intercambio, no existe en BDD"));
        if(!exchange.getStatus().equals(ExchangeStatus.PENDING)) {
            throw new InvalidExchangeException("No puedes acceptar un intercambio que no esta previamente pendiente");
        }
        if(!exchange.getOwner().getId().equals(user.getId())) {
            throw new InvalidExchangeException("Solo puede aceptar este intercambio el dueño de la prenda solicitada");
        }
        exchange.setStatus(ExchangeStatus.ACCEPTED);
        exchange.getOfferedWear().setStatus(WearStatus.RESERVED);
        exchange.getRequestedWear().setStatus(WearStatus.RESERVED);

        return exchangeMapper.toGetExchange(exchangeRepository.save(exchange));
    }

    @Override
    @Transactional
    public GetExchange confirmExchange(Long id) {
        User user = currentUserService.getAuthenticatedUser();
        Exchange exchange = exchangeRepository.findById(id)
                .orElseThrow(() -> new ExchangeNotFoundException("Error al cambiar el estado del intercambio, no existe en BDD"));
        if (!exchange.getStatus().equals(ExchangeStatus.ACCEPTED)) {
            throw new InvalidExchangeException("No puedes confirmar un intercambio que no está previamente aceptado");
        }

        if (exchange.getOwner().getId().equals(user.getId())) {
            exchange.setOwnerConfirmed(true); //confirmo el duenio de la prenda
        } else if (exchange.getRequester().getId().equals(user.getId())) {
            exchange.setRequesterConfirmed(true);//confirmo el solicitante
        } else {
            throw new InvalidExchangeException("Solo pueden confirmar quienes participaron en el intercambio"); //quien realiza la accion no participo en este intercambio
        }

        if (exchange.isOwnerConfirmed() && exchange.isRequesterConfirmed()) {
            exchange.setStatus(ExchangeStatus.COMPLETED); //ambos comfirmaron
        }

        exchange.getOfferedWear().setStatus(WearStatus.EXCHANGED);
        exchange.getRequestedWear().setStatus(WearStatus.EXCHANGED);

        return exchangeMapper.toGetExchange(exchangeRepository.save(exchange));
    }

    @Override
    @Transactional
    public GetExchange rejectExchange(Long id) {
        User user = currentUserService.getAuthenticatedUser();
        Exchange exchange = exchangeRepository.findById(id)
                .orElseThrow(() -> new ExchangeNotFoundException("Error al cambiar el estado del intercambio, no existe en BDD"));
        if(!exchange.getStatus().equals(ExchangeStatus.PENDING)) {
            throw new InvalidExchangeException("No puedes rechazar un intercambio que no esta previamente pendiente");
        }
        if(!exchange.getOwner().getId().equals(user.getId())) {
            throw new InvalidExchangeException("Solo puede rechazar este intercambio el dueño de la prenda solicitada");
        }
        exchange.setStatus(ExchangeStatus.REJECTED);
        return exchangeMapper.toGetExchange(exchangeRepository.save(exchange));
    }

    @Override
    @Transactional
    public GetExchange cancelExchange(Long id) {
        User user = currentUserService.getAuthenticatedUser();
        Exchange exchange = exchangeRepository.findById(id)
                .orElseThrow(() -> new ExchangeNotFoundException("Error al cambiar el estado del intercambio, no existe en BDD"));
        if(exchange.getStatus().equals(ExchangeStatus.CANCELLED) ||exchange.getStatus().equals(ExchangeStatus.COMPLETED)) {
            throw new InvalidExchangeException("No puedes cancelar un intercambio que ya ha sido completado o cancelado");
        }
        if(!(exchange.getOwner().getId().equals(user.getId()) || exchange.getRequester().getId().equals(user.getId()))) {
            throw new InvalidExchangeException("Solo puede cancelar este intercambio si participó en el");
        }
        exchange.setStatus(ExchangeStatus.CANCELLED);
        exchange.getRequestedWear().setStatus(WearStatus.AVAILABLE);
        exchange.getOfferedWear().setStatus(WearStatus.AVAILABLE);
        return exchangeMapper.toGetExchange(exchangeRepository.save(exchange));
    }

    @Override
    public List<GetExchange> getAllExchanges() {
        return exchangeRepository.findAll().stream().map(exchangeMapper::toGetExchange).collect(Collectors.toList());
    }

    @Override
    public GetExchange getById(Long id) {
        return exchangeRepository.findById(id)
                .map(exchangeMapper::toGetExchange)
                .orElseThrow(() -> new ExchangeNotFoundException("Exchange id: "+id+" not found"));
    }

}

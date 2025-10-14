package com.devops.backend.rewear.controllers;

import com.devops.backend.rewear.dtos.request.SaveExchange;
import com.devops.backend.rewear.dtos.response.GetExchange;
import com.devops.backend.rewear.services.ExchangeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetExchange> getById(@PathVariable Long id) {
        return ResponseEntity.ok(exchangeService.getById(id));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<GetExchange>> getAllExchanges(){
        return ResponseEntity.ok(exchangeService.getAllExchanges());
    }

    @PostMapping
    ResponseEntity<GetExchange> saveExchange(@Valid @RequestBody SaveExchange saveExchange){
        return ResponseEntity.ok().body(exchangeService.save(saveExchange));
    }

    @PatchMapping("/{id}/accept")
    ResponseEntity<GetExchange> acceptExchange(@PathVariable Long id){
        return ResponseEntity.ok(exchangeService.acceptExchange(id));
    }

    @PatchMapping("/{id}/confirm")
    ResponseEntity<GetExchange> confirmExchange(@PathVariable Long id){
        return ResponseEntity.ok(exchangeService.confirmExchange(id));
    }

    @PatchMapping("/{id}/reject")
    ResponseEntity<GetExchange> rejectExchange(@PathVariable Long id){
        return ResponseEntity.ok(exchangeService.rejectExchange(id));
    }

    @PatchMapping("/{id}/cancel")
    ResponseEntity<GetExchange> cancelExchange(@PathVariable Long id){
        return ResponseEntity.ok(exchangeService.cancelExchange(id));
    }
}

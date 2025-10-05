package com.devops.backend.rewear.controllers;

import com.devops.backend.rewear.dtos.request.SaveWear;
import com.devops.backend.rewear.dtos.response.GetWear;
import com.devops.backend.rewear.entities.enums.WearStatus;
import com.devops.backend.rewear.services.WearService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wears")
public class WearController {
    private final WearService wearService;

    public WearController(WearService wearService) {
        this.wearService = wearService;
    }

    @GetMapping //vista inicial
    ResponseEntity<List<GetWear>> getAvailableWears(){
        return ResponseEntity.ok(wearService.getAvailableWears());
    }

    @PostMapping //el owner es automaticamente el principal
    public ResponseEntity<GetWear> save(@RequestBody @Valid SaveWear saveWear) {
        return ResponseEntity.ok(wearService.createWear(saveWear));
    }

    //desactivar o activar, solo el propietario y un admin pueden realizar esta accion
    @PatchMapping("/{id}")
    public ResponseEntity<GetWear> updateStatus(@PathVariable Long id,
                                                @RequestParam boolean active) {
        return ResponseEntity.ok(wearService.updateStatus(id, active));
    }
}

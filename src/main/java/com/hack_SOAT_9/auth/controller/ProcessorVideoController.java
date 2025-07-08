package com.hack_SOAT_9.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processor-video")
public class ProcessorVideoController {

    @GetMapping("/get-status")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Sucesso");
    }
}

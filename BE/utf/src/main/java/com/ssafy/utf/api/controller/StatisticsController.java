package com.ssafy.utf.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticsController {

    @GetMapping("/commit")
    public void test(@RequestBody String payload) {
        System.out.println(payload);
    }
}

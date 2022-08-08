package com.ssafy.utf.api.controller;

import com.ssafy.utf.api.service.EmotionService;
import com.ssafy.utf.db.entity.statistics.Emotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    EmotionService emotionService;

    private ArrayList<Emotion> currentEmotions = new ArrayList<>();
    private ArrayList<ArrayList<Emotion>> totalEmotions = new ArrayList<>();

    @PostMapping("/commit")
    public ResponseEntity<Emotion> test(@RequestBody Emotion emotion) {
        currentEmotions.add(emotion);
        if(totalEmotions.size() == 3){
            totalEmotions.add(currentEmotions);
            System.out.println(currentEmotions.toString());
            currentEmotions = new ArrayList<>();
        }
        return new ResponseEntity<Emotion>(emotion, HttpStatus.OK);
    }
}

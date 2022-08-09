package com.ssafy.utf.api.controller;

import com.ssafy.utf.api.service.EmotionService;
import com.ssafy.utf.db.entity.statistics.Emotion;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    EmotionService emotionService;

    // lectureRoom 아이디마다 감정정보 저장할 거 만들어준다 이걸로 전체관리
    // mapping값 /cuurent/{id} 로하면됨
//    private HashMap<String,HashMap<String,ArrayList<Integer>>> lectrueRoomEmotions;
    private HashMap<String, ArrayList<Integer>> emotions = new HashMap<>();

    @PostMapping("/current") //현재 감정정보 받는 함수
    public ResponseEntity<Emotion> getCurrentEmotions(@RequestBody Emotion emotion) {
        String name = emotion.getName();
        int understanding = emotionService.getUnderstanding(emotion); // -1, 0, 1로 구분

        if(emotions.containsKey(name)){ //기존에 있던 학생정보에 감정 추가
            emotions.get(name).add(understanding);
        }else{ //새로운학생
            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(understanding);
            emotions.put(name, scores);
        }
        return new ResponseEntity<Emotion>(emotion, HttpStatus.OK);
    }

    @GetMapping("/current") //현재 이해도 정보 vue로 넘겨주기
    public ResponseEntity<HashMap<Integer, Integer>> getCurrentRatio() {

        HashMap<Integer, Integer> ratios = new HashMap<>();
        ratios.put(-1,0);
        ratios.put(0,0);
        ratios.put(1,0);

        emotions.forEach((key, value) -> {
            System.out.println(key+ " " + value);
            int understanding = emotions.get(key).get(emotions.get(key).size()-1); //마지막 값이 현재 상태
            int newVal = ratios.get(understanding) + 1;
            ratios.replace(understanding, newVal);
        });

        return new ResponseEntity<HashMap<Integer,Integer>>(ratios, HttpStatus.OK);
    }
}

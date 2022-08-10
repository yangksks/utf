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

    private HashMap<String,HashMap<String,ArrayList<Integer>>> lectureRoomEmotions = new HashMap<>();

    @PostMapping("/current/{lectureRoomId}") //현재 감정정보 받는 함수
    public ResponseEntity<Emotion> getCurrentEmotions(@RequestBody Emotion emotion, @PathVariable String lectureRoomId) {
        String name = emotion.getName();
        int understanding = emotionService.getUnderstanding(emotion); // -1, 0, 1로 구분

        if(lectureRoomEmotions.containsKey(lectureRoomId)){ //원래 있는 강의실 id
            if(lectureRoomEmotions.get(lectureRoomId).containsKey(name)){ //기존에 있던 학생
                lectureRoomEmotions.get(lectureRoomId).get(name).add(understanding);
            }else{
                ArrayList<Integer> scores = new ArrayList<>();
                scores.add(understanding);
                lectureRoomEmotions.get(lectureRoomId).put(name, scores); //새로운 ( 학생:[점수] ) 추가
            }
        }else{ //없던 강의실
            HashMap<String, ArrayList<Integer>> newLectureRoom = new HashMap<>(); //새 강의실
            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(understanding);
            newLectureRoom.put(name,scores); // 새강의실에 첫학생 넣기
            lectureRoomEmotions.put(lectureRoomId, newLectureRoom);
        }
        return new ResponseEntity<Emotion>(emotion, HttpStatus.OK);
    }

    private HashMap<String,HashMap<Integer, ArrayList<Integer>>> ratios = new HashMap<>();
    //녹화강의를 위한 시간별 비율저장 ( -1: 이해못함, 1: 이해함, -2: 집중못함, 2:집중함 )

    @GetMapping("/current/{lectureRoomId}") //현재 이해도 정보 vue로 넘겨주기
    public ResponseEntity<HashMap<Integer, Integer>> getCurrentRatio(@PathVariable String lectureRoomId) {

        HashMap<String, ArrayList<Integer>> emotions = lectureRoomEmotions.get(lectureRoomId);
        int understand = 0; //각 시간에서 비율 구하기 위한 변수들
        int neutral = 0;
        int notUnderstand = 0;

        if(emotions != null){
            for(String name : emotions.keySet()){ // 각학생들의 이해도정보에 대해
                int understanding = emotions.get(name).get(emotions.get(name).size()-1); //학생의 현재 이해도 정보
                // 개수 증가
                if(understanding == -1){
                    notUnderstand++;
                }else if(understanding == 0){
                    neutral++;
                }else{
                    understand++;
                }
            }
        }

        if( ratios.containsKey(lectureRoomId) ){ // 강의 진행중
            ratios.get(lectureRoomId).get(-1).add(notUnderstand);
            ratios.get(lectureRoomId).get(0).add(neutral);
            ratios.get(lectureRoomId).get(1).add(understand);

        }else{ // 안하던 강의
            HashMap<Integer,ArrayList<Integer>> newRatio = new HashMap<>();
            ArrayList<Integer> not = new ArrayList<>();
            ArrayList<Integer> neu = new ArrayList<>();
            ArrayList<Integer> un = new ArrayList<>();
            not.add(notUnderstand);
            neu.add(neutral);
            un.add(understand);

            newRatio.put(-1,not);
            newRatio.put(0,neu);
            newRatio.put(1,un);

            ratios.put(lectureRoomId, newRatio);
        }

        HashMap<Integer, Integer> front = new HashMap<>();
        front.put(-1,notUnderstand);
        front.put(0,neutral);
        front.put(1,understand);

        return new ResponseEntity<HashMap<Integer,Integer>>(front, HttpStatus.OK);
    }

    @PostMapping("/end/{lectureRoomId}")
    public ResponseEntity<String> endLecture(@PathVariable String lectureRoomId){ //강의 종료하고 DB올리기
        HashMap<Integer, ArrayList<Integer>> recordRatio = ratios.get(lectureRoomId);
        ratios.remove(lectureRoomId);

        System.out.println(recordRatio.toString());
        emotionService.insertLecture(recordRatio);
        return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
    }
}

package com.ssafy.utf.api.controller;

import com.ssafy.utf.api.service.LectureRoomService;
import com.ssafy.utf.api.service.StatisticsService;
import com.ssafy.utf.db.entity.statistics.Emotion;
import com.ssafy.utf.db.entity.statistics.Focus;
import com.ssafy.utf.db.entity.statistics.RecentLecture;
import com.ssafy.utf.db.entity.statistics.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;
    @Autowired
    LectureRoomService lectureRoomService;

    private HashMap<String,HashMap<String,ArrayList<Integer>>> lectureRoomUnderstanding = new HashMap<>();
    private HashMap<String,HashMap<String,ArrayList<Integer>>> lectureRoomFocus = new HashMap<>();

    @PostMapping("/current/{lectureRoomId}") //현재 감정정보 받는 함수
    public ResponseEntity<Emotion> getCurrentEmotions(@RequestBody Emotion emotion, @PathVariable String lectureRoomId) {
        String name = emotion.getName();
        int understanding = statisticsService.getUnderstanding(emotion); // -1, 0, 1로 구분

        if(lectureRoomUnderstanding.containsKey(lectureRoomId)){ //원래 있는 강의실 id
            if(lectureRoomUnderstanding.get(lectureRoomId).containsKey(name)){ //기존에 있던 학생
                lectureRoomUnderstanding.get(lectureRoomId).get(name).add(understanding);
            }else{
                ArrayList<Integer> scores = new ArrayList<>();
                scores.add(understanding);
                lectureRoomUnderstanding.get(lectureRoomId).put(name, scores); //새로운 ( 학생:[점수] ) 추가
            }
        }else{ //없던 강의실
            HashMap<String, ArrayList<Integer>> newLectureRoom = new HashMap<>(); //새 강의실
            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(understanding);
            newLectureRoom.put(name,scores); // 새강의실에 첫학생 넣기
            lectureRoomUnderstanding.put(lectureRoomId, newLectureRoom);
        }
        return new ResponseEntity<Emotion>(emotion, HttpStatus.OK);
    }

    private HashMap<String,HashMap<Integer, ArrayList<Integer>>> ratios = new HashMap<>();
    //녹화강의를 위한 시간별 비율저장 ( -1: 이해못함, 1: 이해함, -2: 집중못함, 2:집중함 )

    @GetMapping("/current/{lectureRoomId}") //현재 이해도 정보 vue로 넘겨주기
    public ResponseEntity<HashMap<Integer, Integer>> getCurrentUnderstandRatio(@PathVariable String lectureRoomId) {

        HashMap<String, ArrayList<Integer>> emotions = lectureRoomUnderstanding.get(lectureRoomId);
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

        HashMap<Integer, ArrayList<Integer>> recordRatio = ratios.get(lectureRoomId); //강의의 통계 가져오기
        ArrayList<Integer> notUnderstand = recordRatio.get(-1);
        ArrayList<Integer> neutral = recordRatio.get(0);
        ArrayList<Integer> understand = recordRatio.get(1);
        ArrayList<Integer> notFocus = recordRatio.get(-2);
        ArrayList<Integer> focus = recordRatio.get(2);

        ratios.remove(lectureRoomId);
        Date now = new Date();
        SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //현재시각 DATETIME 형식으로

        Statistics st = new Statistics();
        st.setLecture_room_id(1);
        st.setTime(datetime.toString());
        st.setUnderstand(understand);
        st.setNeutral(neutral);
        st.setNotUnderstand(notUnderstand);
        st.setNotFocus(notFocus);
        st.setFocus(focus);

        statisticsService.insertLecture(st); // DB저장
        return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
    }

    @GetMapping("/record/understand/{statistics_id}")
    public ResponseEntity<HashMap<Integer,ArrayList<Integer>>> getRecordedUnderstand(@PathVariable long statistics_id){
        HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();

        ArrayList<Integer> recordUnderstand = statisticsService.getRecordedUnderstand(statistics_id);
        ArrayList<Integer> recordNeutral = statisticsService.getRecordedNeutral(statistics_id);
        ArrayList<Integer> recordNotUnderstand = statisticsService.getRecordedNotUnderstand(statistics_id);
        ArrayList<Integer> recordFocus = statisticsService.getRecordedFocus(statistics_id);
        ArrayList<Integer> recordNotFocus = statisticsService.getRecordedNotFocus(statistics_id);

        map.put(-2, recordNotFocus);
        map.put(-1,recordNotUnderstand);
        map.put(0,recordNeutral);
        map.put(1,recordUnderstand);
        map.put(2, recordFocus);

        return new ResponseEntity<HashMap<Integer,ArrayList<Integer>>>(map, HttpStatus.OK);
    }

    @PostMapping("/focus/current/{lectureRoomId}")
    public ResponseEntity<Focus> getCurrentFocus(@RequestBody Focus focus, @PathVariable String lectureRoomId) {

        String name = focus.getName();
        int isFocus = focus.getIsFocus() ? 2 : -2; //집중하면 2 , 안하면 -2

        if(lectureRoomFocus.containsKey(lectureRoomId)){ //원래 있는 강의실 id
            if(lectureRoomFocus.get(lectureRoomId).containsKey(name)){ //기존에 있던 학생
                lectureRoomFocus.get(lectureRoomId).get(name).add(isFocus);
            }else{
                ArrayList<Integer> scores = new ArrayList<>();
                scores.add(isFocus);
                lectureRoomFocus.get(lectureRoomId).put(name, scores); //새로운 ( 학생:[점수] ) 추가
            }
        }else{ //없던 강의실
            HashMap<String, ArrayList<Integer>> newLectureRoom = new HashMap<>(); //새 강의실
            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(isFocus);
            newLectureRoom.put(name,scores); // 새강의실에 첫학생 넣기
            lectureRoomFocus.put(lectureRoomId, newLectureRoom);
        }
        return new ResponseEntity<Focus>(focus, HttpStatus.OK);
    }

    @GetMapping("/focus/current/{lectureRoomId}")
    public ResponseEntity<HashMap<Integer, Integer>> getCurrentFocusRatio(@PathVariable String lectureRoomId) {

        HashMap<String, ArrayList<Integer>> focuses = lectureRoomFocus.get(lectureRoomId);
        int focus = 0; //각 시간에서 비율 구하기 위한 변수들
        int notFocus = 0;

        if(focuses != null){
            for(String name : focuses.keySet()){ // 각학생들의 이해도정보에 대해
                int focusing = focuses.get(name).get(focuses.get(name).size()-1); //학생의 현재 집중도 정보
                // 개수 증가
                if(focusing == -2){
                    notFocus++;
                }else{
                    focus++;
                }
            }
        }
        if(focus + notFocus != 0){ //비율로 바꿔주기
            int n = focus + notFocus;
            focus = (focus*100) / n;
            notFocus = (notFocus*100) / n;
        }

        if( ratios.containsKey(lectureRoomId) ){ // 강의 진행중
            ratios.get(lectureRoomId).get(-2).add(notFocus);
            ratios.get(lectureRoomId).get(2).add(focus);

        }else{ // 안하던 강의
            HashMap<Integer,ArrayList<Integer>> newRatio = new HashMap<>();
            ArrayList<Integer> F = new ArrayList<>();
            ArrayList<Integer> T = new ArrayList<>();
            F.add(notFocus);
            T.add(focus);

            newRatio.put(-2,F);
            newRatio.put(2,T);

            ratios.put(lectureRoomId, newRatio);
        }

        HashMap<Integer, Integer> front = new HashMap<>(); // front로 넘겨줄 것
        front.put(-2,notFocus);
        front.put(2,focus);

        return new ResponseEntity<HashMap<Integer,Integer>>(front, HttpStatus.OK);
    }
    @GetMapping("/recent/{userId}")
    public ResponseEntity<ArrayList<RecentLecture>> getRecentlyLectures(@PathVariable long userId){
        ArrayList<RecentLecture> recentLecture = lectureRoomService.getRecentLectures(userId);
        return new ResponseEntity<ArrayList<RecentLecture>>(recentLecture, HttpStatus.OK);
    }
}

package com.ssafy.utf.api.controller;

import com.ssafy.utf.api.service.LectureRoomService;
import com.ssafy.utf.api.service.LectureService;
import com.ssafy.utf.db.entity.Lecture;
import com.ssafy.utf.db.entity.LectureRoom;
import com.ssafy.utf.db.entity.statistics.RecentLecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/lectureRoom")
@CrossOrigin(origins = "*")
public class LectureController {

    @Autowired
    LectureRoomService lectureRoomService;
    @Autowired
    LectureService lectureService;

    @GetMapping("/recent/{userId}")
    public ResponseEntity<ArrayList<RecentLecture>> getRecentlyLectures(@PathVariable long userId){ //최근 5개강의 조회
        ArrayList<RecentLecture> recentLecture = lectureRoomService.getRecentLectures(userId);
        return new ResponseEntity<ArrayList<RecentLecture>>(recentLecture, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<ArrayList<LectureRoom>> getAllLectureRooms(@PathVariable long userId){
        ArrayList<LectureRoom> lectureRooms = lectureRoomService.getLectureRooms(userId);
        return new ResponseEntity<ArrayList<LectureRoom>>(lectureRooms, HttpStatus.OK);
    }

    @GetMapping("/lecture/{lectureRoomId}")
    public ResponseEntity<ArrayList<Lecture>> getAllLectures(@PathVariable long lectureRoomId){ //강의실 들어왔을 떄 모든 강의 조회
        ArrayList<Lecture> lectures = lectureService.getLectures(lectureRoomId);
        return new ResponseEntity<ArrayList<Lecture>>(lectures,HttpStatus.OK);
    }

    @GetMapping("/lecture/one/{videoId}")
    public ResponseEntity<Lecture> getOneLecture(@PathVariable long videoId){ //강의 한개 조회
        Lecture lecture = lectureService.getLecture(videoId);
        return new ResponseEntity<Lecture>(lecture, HttpStatus.OK);
    }
}

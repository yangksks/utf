package com.ssafy.utf.api.controller;

import com.ssafy.utf.api.request.LectureRoomRegistReq;
import com.ssafy.utf.api.request.RecordingReq;
import com.ssafy.utf.api.service.LectureRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/lectureRoom")
@CrossOrigin(origins = "*")
public class LectureRoomController {
    @Autowired
    LectureRoomService lectureRoomService;

    @PostMapping
    public ResponseEntity<Object> registLectureRoom(@RequestBody LectureRoomRegistReq lectureRoomRegistReq) {
        System.out.println(lectureRoomRegistReq);
        HashMap<String, Object> result = new HashMap<>();
        HttpStatus status = null;
        try {
            result.put("lectureRoom", lectureRoomService.registLectureRoom(lectureRoomRegistReq));
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return ResponseEntity.status(status).body(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getLectureRoomList(@PathVariable("userId") long userId) {
        HashMap<String, Object> result = new HashMap<>();
        HttpStatus status = null;
        try {
            result.put("lectureRoomList", lectureRoomService.getLectureRoomList(userId));
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return ResponseEntity.status(status).body(result);
    }

    @DeleteMapping("/{lectureRoomId}")
    public ResponseEntity<Object> deleteLectureRoom(@PathVariable("lectureRoomId") long lectureRoomId) {
        HashMap<String, Object> result = new HashMap<>();
        HttpStatus status = null;
        try {
            lectureRoomService.deleteLectureRoom(lectureRoomId);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return ResponseEntity.status(status).build();
    }

    @PostMapping("/record")
    public ResponseEntity<Object> registRecordVideo(@RequestBody RecordingReq recordingReq) {
        HttpStatus status = null;
        try {
            lectureRoomService.registRecordVideo(recordingReq);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }

        return ResponseEntity.status(status).build();
    }

}

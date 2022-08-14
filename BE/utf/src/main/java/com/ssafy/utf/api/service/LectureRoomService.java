package com.ssafy.utf.api.service;

import com.ssafy.utf.api.request.LectureRoomRegistReq;
import com.ssafy.utf.db.entity.lecture.LectureRoom;

import java.util.List;

public interface LectureRoomService {
    LectureRoom registLectureRoom(LectureRoomRegistReq lectureRoomRegistReq) throws InterruptedException;

    List<LectureRoom> getLectureRoomList(long userId);

    void deleteLectureRoom(long lectureRoomId);
}

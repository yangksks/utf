package com.ssafy.utf.api.service;


import com.ssafy.utf.api.request.LectureRoomRegistReq;
import com.ssafy.utf.common.util.RandomCodeGenerator;
import com.ssafy.utf.db.entity.lecture.Lecture;
import com.ssafy.utf.db.entity.lecture.LectureRoom;
import com.ssafy.utf.db.entity.statistics.RecentLecture;
import com.ssafy.utf.db.repository.LectureRepository;
import com.ssafy.utf.db.repository.LectureRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LectureRoomServiceImpl implements LectureRoomService {
    @Autowired
    LectureRoomRepository lectureRoomRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public LectureRoom registLectureRoom(LectureRoomRegistReq lectureRoomRegistReq) throws InterruptedException {
        LectureRoom lectureRoom = new LectureRoom();
        while (true) {
            String lectureRoomCode = new RandomCodeGenerator().getCode(12);
            System.out.println("코드 생성" + lectureRoomCode);
            LectureRoom checkLectureRoom = lectureRoomRepository.findByLectureRoomCode(lectureRoomCode);
            if (checkLectureRoom == null) {
                lectureRoom.setUserId(lectureRoomRegistReq.getUserId());
                lectureRoom.setTitle(lectureRoomRegistReq.getTitle());
                lectureRoom.setSubject(lectureRoomRegistReq.getSubject());
                lectureRoom.setLectureRoomCode(lectureRoomCode);
                break;
            }
            Thread.sleep(500);
        }
        return lectureRoomRepository.save(lectureRoom);
    }

    @Override
    public List<LectureRoom> getLectureRoomList(long userId) {
        return lectureRoomRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteLectureRoom(long lectureRoomId) {
        lectureRoomRepository.deleteByLectureRoomId(lectureRoomId);
    }

    @Override
    public List<RecentLecture> getRecentLectures(long userId) {
        List<RecentLecture> recentLectures = new ArrayList<>();

        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllByUserId(userId);
        for (LectureRoom lectureRoom : lectureRooms) {
            long lectureRoomId = lectureRoom.getLectureRoomId();
            List<Lecture> lectures = lectureRepository.findByLectureRoomIdOrderByStartTimeDesc(lectureRoomId);
            for (Lecture lecture: lectures){
                RecentLecture recentLecture = new RecentLecture();
                recentLecture.setLectureRoomId(lectureRoomId);
                recentLecture.setTitle(lectureRoom.getTitle());
                recentLecture.setStartTime(lecture.getStartTime());
                recentLecture.setLectureId(lecture.getLectureId());

                recentLectures.add(recentLecture);
            }
        }
        Collections.sort(recentLectures);
        List<RecentLecture> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i >= recentLectures.size()) break;
            result.add(recentLectures.get(i));
        }
        return result;
    }

}

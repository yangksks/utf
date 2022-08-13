package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.Lecture;
import com.ssafy.utf.db.entity.LectureRoom;
import com.ssafy.utf.db.entity.statistics.RecentLecture;
import com.ssafy.utf.db.repository.LectureRepository;
import com.ssafy.utf.db.repository.LectureRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class LectureRoomServiceImpl implements LectureRoomService {

    @Autowired
    LectureRoomRepository lectureRoomRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Override
    public ArrayList<RecentLecture> getRecentLectures(long userId) {
        ArrayList<RecentLecture> recentLectures = new ArrayList<>();

        ArrayList<LectureRoom> lectureRooms = lectureRoomRepository.findByUserId(userId);
        for(LectureRoom lectureRoom : lectureRooms){
            long lectureRoomId = lectureRoom.getLectureRoomId();
            ArrayList<Lecture> lectures = lectureRepository.findByLectureRoomId(lectureRoomId);
            for (Lecture lecture: lectures){
                RecentLecture recentLecture = new RecentLecture();
                recentLecture.setLectureRoomId(lectureRoomId);
                recentLecture.setTitle(lectureRoom.getTitle());
                recentLecture.setStartTime(lecture.getStartTime());

                recentLectures.add(recentLecture);
            }
        }
        Collections.sort(recentLectures);
        ArrayList<RecentLecture> result = new ArrayList<>();
        for(int i=0; i<5; i++){
            if(i >= recentLectures.size()) break;
            result.add(recentLectures.get(i));
        }
        return result;
    }
}

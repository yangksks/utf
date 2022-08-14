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
public class LectureServiceImpl implements LectureService {

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public ArrayList<Lecture> getLectures(long lectureRoomId) {
        return lectureRepository.findByLectureRoomIdOrderByStartTimeDesc(lectureRoomId);
    }

    @Override
    public Lecture getLecture(long videoId) {
        return lectureRepository.findByVideoId(videoId);
    }
}

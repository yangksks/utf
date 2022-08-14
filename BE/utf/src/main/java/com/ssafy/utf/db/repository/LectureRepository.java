package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    ArrayList<Lecture> findByLectureRoomIdOrderByStartTimeDesc(long lectureRoomId);

    Lecture findByVideoId(long videoId);
}

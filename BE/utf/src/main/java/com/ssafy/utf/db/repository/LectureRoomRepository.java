package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.lecture.LectureRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LectureRoomRepository extends JpaRepository<LectureRoom, Integer> {
    LectureRoom findByLectureRoomCode(String lectureRoomCode);

    List<LectureRoom> findAllByUserId(long userId);

    @Modifying
    @Transactional
    void deleteByLectureRoomId(long lectureRoomId);

}

package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.LectureRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface LectureRoomRepository extends JpaRepository<LectureRoom, Long> {

    ArrayList<LectureRoom> findByUserId(long userId);
}

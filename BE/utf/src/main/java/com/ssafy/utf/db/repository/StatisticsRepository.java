package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.LectureRoom;
import com.ssafy.utf.db.entity.statistics.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, String> {
    @Query(value = "SELECT understand FROM statistics WHERE video_id = :video_id", nativeQuery = true)
    String findUnderstandByVideoId(long video_id);

    @Query(value = "SELECT neutral FROM statistics WHERE video_id = :video_id", nativeQuery = true)
    String findNeutralByVideoId(long video_id);

    @Query(value = "SELECT not_understand FROM statistics WHERE video_id = :video_id", nativeQuery = true)
    String findNotUnderstandByVideoId(long video_id);

    @Query(value = "SELECT focus FROM statistics WHERE video_id = :video_id", nativeQuery = true)
    String findFocusByVideoId(long video_id);

    @Query(value = "SELECT not_focus FROM statistics WHERE video_id = :video_id", nativeQuery = true)
    String findNotFocusByVideoId(long video_id);
}

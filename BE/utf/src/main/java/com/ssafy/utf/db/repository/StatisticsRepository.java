package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.statistics.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, String> {
    @Query(value = "SELECT understand FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findUnderstandByStatisticsId(@Param("statisticsId") long statisticsId);

    @Query(value = "SELECT neutral FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findNeutralByStatisticsId(@Param("statisticsId") long statisticsId);

    @Query(value = "SELECT not_understand FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findNotUnderstandByStatisticsId(@Param("statisticsId") long statisticsId);

    @Query(value = "SELECT focus FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findFocusByStatisticsId(@Param("statisticsId") long statisticsId);

    @Query(value = "SELECT not_focus FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findNotFocusByStatisticsId(@Param("statisticsId") long statisticsId);
}

package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.statistics.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, String> {
    @Query(value = "SELECT understand FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findUnderstandByUnderstandStatisticsId(@Param("statisticsId") long statisticsId);

    @Query(value = "SELECT neutral FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findNeutralByUnderstandStatisticsId(@Param("statisticsId") long statisticsId);

    @Query(value = "SELECT not_understand FROM statistics WHERE statistics_id = :statisticsId", nativeQuery = true)
    String findNotUnderstandByUnderstandStatisticsId(@Param("statisticsId") long statisticsId);
}

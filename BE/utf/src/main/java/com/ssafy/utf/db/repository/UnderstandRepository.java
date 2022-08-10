package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.statistics.UnderstandStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnderstandRepository extends JpaRepository<UnderstandStatistics, String> {

}

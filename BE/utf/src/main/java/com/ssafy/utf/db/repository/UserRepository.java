package com.ssafy.utf.db.repository;

import com.ssafy.utf.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user WHERE social_login_type=1 AND social_id=:socialId", nativeQuery = true)
    User findByKakaoSocialId(@Param("socialId") long socialId);

    @Query(value = "SELECT * FROM user WHERE social_login_type=2 AND social_id=:socialId", nativeQuery = true)
    User findByNaverSocialId(@Param("socialId") String socialId);

}

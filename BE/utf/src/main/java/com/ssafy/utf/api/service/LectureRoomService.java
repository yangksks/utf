package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.LectureRoom;
import com.ssafy.utf.db.entity.statistics.RecentLecture;

import java.util.ArrayList;

public interface LectureRoomService {
    ArrayList<RecentLecture> getRecentLectures(long userId);

    ArrayList<LectureRoom> getLectureRooms(long userId);
}

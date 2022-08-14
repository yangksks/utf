package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import com.ssafy.utf.db.entity.statistics.RecentLecture;
import com.ssafy.utf.db.entity.statistics.Statistics;

import java.util.ArrayList;

public interface StatisticsService {
    int getUnderstanding(Emotion e);

    void insertLecture(Statistics st);

    ArrayList<Integer> getRecordedUnderstand(long statisticsId);
    ArrayList<Integer> getRecordedNeutral(long statisticsId);
    ArrayList<Integer> getRecordedNotUnderstand(long statisticsId);

    ArrayList<Integer> getRecordedFocus(long statistics_id);

    ArrayList<Integer> getRecordedNotFocus(long statistics_id);
}

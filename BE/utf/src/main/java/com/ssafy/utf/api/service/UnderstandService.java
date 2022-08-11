package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import com.ssafy.utf.db.entity.statistics.Statistics;

import java.util.ArrayList;

public interface UnderstandService {
    int getUnderstanding(Emotion e);

    void insertLecture(Statistics st);

    ArrayList<Integer> getRecordedUnderstand(long understandStatisticsId);
    ArrayList<Integer> getRecordedNeutral(long understandStatisticsId);
    ArrayList<Integer> getRecordedNotUnderstand(long understandStatisticsId);

}

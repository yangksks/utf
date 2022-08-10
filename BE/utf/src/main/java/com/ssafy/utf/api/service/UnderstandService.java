package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import com.ssafy.utf.db.entity.statistics.UnderstandStatistics;

public interface UnderstandService {
    int getUnderstanding(Emotion e);

    void insertLecture(UnderstandStatistics test);
}

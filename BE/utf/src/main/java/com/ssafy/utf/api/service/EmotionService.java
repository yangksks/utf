package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

public interface EmotionService {
    int getUnderstanding(Emotion e);
    int insertLecture(HashMap<Integer, ArrayList<Integer>> map);
}

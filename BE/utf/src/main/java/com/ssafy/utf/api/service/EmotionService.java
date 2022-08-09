package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import org.springframework.stereotype.Service;

public interface EmotionService {
    int getUnderstanding(Emotion e);
}

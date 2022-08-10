package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import com.ssafy.utf.db.entity.statistics.UnderstandStatistics;
import com.ssafy.utf.db.repository.UnderstandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnderstandServiceImpl implements UnderstandService {

    @Autowired
    private UnderstandRepository understandRepository;
    @Override
    public int getUnderstanding(Emotion e) {
        String emotion = e.getEmotion();
        if( emotion.equals("angry") ){
            return -1;
        }
        else if( emotion.equals("disgusted") ){
            return -1;
        }
        else if( emotion.equals("fearful") ){
            return -1;
        }
        else if( emotion.equals("happy") ){
            return 1;
        }
        else if( emotion.equals("sad") ){
            return -1;
        }
        else if( emotion.equals("surprised") ){
            return 1;
        }
        else{ //neutral
            if(e.getScore() > 0.99) return 0;
            else return 1;
        }
    }


    @Override
    public void insertLecture(UnderstandStatistics us) {
        understandRepository.save(us);
    }
}

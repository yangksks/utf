package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import com.ssafy.utf.db.entity.statistics.Statistics;
import com.ssafy.utf.db.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UnderstandServiceImpl implements UnderstandService {

    @Autowired
    private StatisticsRepository understandRepository;
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
    public void insertLecture(Statistics st) {
        understandRepository.save(st);
    }

    @Override
    public ArrayList<Integer> getRecordedUnderstand(long understandStatisticsId) {
        String str = understandRepository.findUnderstandByUnderstandStatisticsId(understandStatisticsId);

        int len = str.length();
        String[] temp = str.substring(1,len-1).split(",");

        ArrayList<Integer> recordUnderstand = new ArrayList<>();
        for(String s: temp){
            recordUnderstand.add(Integer.parseInt(s.trim()));
        }
        return recordUnderstand;
    }

    @Override
    public ArrayList<Integer> getRecordedNeutral(long understandStatisticsId) {
        String str = understandRepository.findNeutralByUnderstandStatisticsId(understandStatisticsId);

        int len = str.length();
        String[] temp = str.substring(1,len-1).split(",");

        ArrayList<Integer> recordNeutral = new ArrayList<>();
        for(String s: temp){
            recordNeutral.add(Integer.parseInt(s.trim()));
        }
        return recordNeutral;
    }

    @Override
    public ArrayList<Integer> getRecordedNotUnderstand(long understandStatisticsId) {
        String str = understandRepository.findNotUnderstandByUnderstandStatisticsId(understandStatisticsId);
        int len = str.length();
        String[] temp = str.substring(1,len-1).split(",");

        ArrayList<Integer> recordNotUnderstand = new ArrayList<>();
        for(String s: temp){
            recordNotUnderstand.add(Integer.parseInt(s.trim()));
        }
        return recordNotUnderstand;
    }
}

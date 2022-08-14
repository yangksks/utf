package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.statistics.Emotion;
import com.ssafy.utf.db.entity.statistics.Statistics;
import com.ssafy.utf.db.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;
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
        statisticsRepository.save(st);
    }

    private ArrayList<Integer> StringToArrayList(String from){
        int len = from.length();
        String[] temp = from.substring(1,len-1).split(",");

        ArrayList<Integer> to = new ArrayList<>();
        for(String s: temp){
            to.add(Integer.parseInt(s.trim()));
        }
        return to;
    }
    @Override
    public ArrayList<Integer> getRecordedUnderstand(long statisticsId) {
        String str = statisticsRepository.findUnderstandByStatisticsId(statisticsId);

        ArrayList<Integer> recordUnderstand = StringToArrayList(str);
        return recordUnderstand;
    }

    @Override
    public ArrayList<Integer> getRecordedNeutral(long statisticsId) {
        String str = statisticsRepository.findNeutralByStatisticsId(statisticsId);

        ArrayList<Integer> recordNeutral = StringToArrayList(str);
        return recordNeutral;
    }

    @Override
    public ArrayList<Integer> getRecordedNotUnderstand(long statisticsId) {
        String str = statisticsRepository.findNotUnderstandByStatisticsId(statisticsId);

        ArrayList<Integer> recordNotUnderstand = StringToArrayList(str);
        return recordNotUnderstand;
    }

    @Override
    public ArrayList<Integer> getRecordedFocus(long statisticsId) {
        String str = statisticsRepository.findFocusByStatisticsId(statisticsId);

        ArrayList<Integer> recordFocus = StringToArrayList(str);
        return recordFocus;
    }

    @Override
    public ArrayList<Integer> getRecordedNotFocus(long statisticsId) {
        String str = statisticsRepository.findNotFocusByStatisticsId(statisticsId);

        ArrayList<Integer> recordNotFocus = StringToArrayList(str);
        return recordNotFocus;
    }
}

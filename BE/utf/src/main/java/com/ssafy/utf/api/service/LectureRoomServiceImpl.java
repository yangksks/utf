package com.ssafy.utf.api.service;

import com.ssafy.utf.api.request.LectureRoomRegistReq;
import com.ssafy.utf.common.util.RandomCodeGenerator;
import com.ssafy.utf.db.entity.lecture.LectureRoom;
import com.ssafy.utf.db.repository.LectureRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureRoomServiceImpl implements LectureRoomService {
    @Autowired
    LectureRoomRepository lectureRoomRepository;

    @Override
    public LectureRoom registLectureRoom(LectureRoomRegistReq lectureRoomRegistReq) throws InterruptedException {
        LectureRoom lectureRoom = new LectureRoom();
        while (true) {
            String lectureRoomCode = new RandomCodeGenerator().getCode(12);
            System.out.println("코드 생성" + lectureRoomCode);
            LectureRoom checkLectureRoom = lectureRoomRepository.findByLectureRoomCode(lectureRoomCode);
            if (checkLectureRoom == null) {
                lectureRoom.setUserId(lectureRoomRegistReq.getUserId());
                lectureRoom.setTitle(lectureRoomRegistReq.getTitle());
                lectureRoom.setLectureRoomCode(lectureRoomCode);
                break;
            }
            Thread.sleep(500);
        }
        return lectureRoomRepository.save(lectureRoom);
    }

    @Override
    public List<LectureRoom> getLectureRoomList(long userId) {
        return lectureRoomRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteLectureRoom(long lectureRoomId) {
        lectureRoomRepository.deleteByLectureRoomId(lectureRoomId);
    }
}

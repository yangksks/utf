package com.ssafy.utf.db.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LectureRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lectureRoomId;
    private long userId;
    private String title;
    private String lectureRoomCode;
    private int is_active;

}


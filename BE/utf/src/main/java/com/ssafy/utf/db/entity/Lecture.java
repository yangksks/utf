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
public class Lecture{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long videoId;
    private long lectureRoomId;
    private String videoUrl;
    private String startTime;
    private String endTime;
    private String chatRecord;

}

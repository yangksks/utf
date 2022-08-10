package com.ssafy.utf.db.entity.statistics;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UnderstandStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long understand_statistics_id;
    private int lecture_room_id;
    private String time;
    @Convert(converter = UnderstandConverter.class)
    private ArrayList<Integer> notUnderstand;
    @Convert(converter = UnderstandConverter.class)
    private ArrayList<Integer> neutral;
    @Convert(converter = UnderstandConverter.class)
    private ArrayList<Integer> understand;

}

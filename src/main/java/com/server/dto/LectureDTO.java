package com.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LectureDTO implements Serializable {
    private Long id;
    private String title;
    private String filename;
    private byte[] attachment;
    private LocalDate date;
}

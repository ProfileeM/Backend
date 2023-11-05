package com.example.profileem.domain.dto;

import com.example.profileem.domain.Notice;
import lombok.*;

import java.util.Date;

// 수정중

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRequestDto {
    private Long noticeId;  // Id

    private String title;   // 제목

    private String content; // 내용

    private Date creationDate;


}

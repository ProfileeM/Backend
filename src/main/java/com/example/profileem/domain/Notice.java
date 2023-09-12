package com.example.profileem.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;  // Id

    @Column(name = "title")
    private String title;   // 제목

    @Column(name = "content")
    private String content; // 내용

    @Column(name = "creatton_date")
    @CreationTimestamp
    private Date creationDate;

    @Builder
    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
        this.creationDate = new Date();
    }
}

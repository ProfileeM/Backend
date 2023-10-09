package com.example.profileem.controller;

import com.example.profileem.domain.Card;
import com.example.profileem.domain.Notice;
import com.example.profileem.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@Tag(name = "Notice")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {this.noticeService = noticeService; }

    // 공지사항 등록 (관리자만 하도록 수정해야함)
    @Operation(summary = "공지사항 등록", description = "공지사항 등록")
    @PostMapping("/")
    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
        Notice createdNotice = noticeService.createNotice(notice);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotice);
    }

    // 공지사항 전체 조회
    @Operation(summary = "공지사항 전체 조회", description = "공지사항 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<List<Notice>> getAllNotices() {
        List<Notice> notices = noticeService.getAllNotices();
        return ResponseEntity.status(HttpStatus.OK).body(notices);
    }
    // 공지사항 하나 조회
    @Operation(summary = "공지사항 하나 조회", description = "공지사항 하나 조회")
    @GetMapping("/{notice_id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Long notice_id) {
        Optional<Notice> notice = noticeService.getNoticeById(notice_id);
        return notice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 공지사항 수정 (관리자만 하도록 수정해야함)

    @Operation(summary = "공지사항 수정", description = "공지사항 수정")
    @PutMapping("/{notice_id}")
    public ResponseEntity<Notice> updateNotice(@PathVariable Long notice_id, @RequestBody Notice newNotice) {
        Notice updatedNotice = noticeService.updateNotice(notice_id, newNotice);
        return ResponseEntity.status(HttpStatus.OK).body(updatedNotice);
    }

    // 공지사항 삭제 (관리자만 하도록 수정해야함)
    @Operation(summary = "공지사항 삭제", description = "공지사항 삭제")
    @DeleteMapping("/{notice_id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long notice_id) {
        noticeService.deleteNotice(notice_id);
        return ResponseEntity.noContent().build();
    }

}

package com.example.profileem.service;

import com.example.profileem.domain.Notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface NoticeService {
    Notice createNotice(Notice notice);

    List<Notice> getAllNotices();

    Optional<Notice> getNoticeById(Long notice_id);

    Notice updateNotice(Long notice_id, Notice newNotice);

    void deleteNotice(Long notice_id);
}

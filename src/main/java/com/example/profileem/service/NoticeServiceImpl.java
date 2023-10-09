package com.example.profileem.service;

import com.example.profileem.controller.NoticeController;
import com.example.profileem.domain.Notice;
import com.example.profileem.repository.NoticeRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService{
    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) { this.noticeRepository = noticeRepository;}

    @Override
    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    @Override
    public Optional<Notice> getNoticeById(Long notice_id){
        return noticeRepository.findById(notice_id);
    }

    @Override
    public Notice updateNotice(Long notice_id, Notice newNotice) {
        Notice oldNotice = noticeRepository.findById(notice_id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));

        oldNotice.setTitle(newNotice.getTitle());
        oldNotice.setContent(newNotice.getContent());

        return noticeRepository.save(oldNotice);
    }

    @Override
    public void deleteNotice(Long notice_id) {
        noticeRepository.deleteById(notice_id);
    }
}

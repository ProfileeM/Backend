package com.example.profileem.controller;

import com.example.profileem.domain.Notice;
import com.example.profileem.service.NoticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NoticeControllerTest {

    @Mock
    private NoticeService noticeService;

    @InjectMocks
    private NoticeController noticeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotice() {
        // 테스트용 Notice 객체 생성
        Notice testNotice = new Notice();
        testNotice.setNoticeId(1L);
        testNotice.setTitle("testTitle");
        testNotice.setContent("testContent");

        when(noticeService.createNotice(any())).thenReturn(testNotice);

        ResponseEntity<Notice> responseEntity = noticeController.createNotice(new Notice("testTitle", "testContent"));

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // 응답 본문에서 공지사항 ID 확인
        Notice createdNotice = responseEntity.getBody();
        assertEquals(testNotice, createdNotice);

    }

    @Test
    void testGetAllNotices() {
        // 테스트용 Notice 리스트 생성
        List<Notice> testNotices = new ArrayList<>();
        Notice testNotice1 = new Notice();testNotice1.setNoticeId(1L);
        testNotice1.setTitle("Test Notice 1");
        testNotice1.setContent("This is test notice 1");
        Notice testNotice2 = new Notice();testNotice2.setNoticeId(2L);
        testNotice2.setTitle("Test Notice 2");
        testNotice2.setContent("This is test notice 2");
        testNotices.add(testNotice1);
        testNotices.add(testNotice2);

        // NoticeService의 getAllNotices 메서드가 호출될 때 테스트용 Notice 리스트를 반환하도록 설정
        when(noticeService.getAllNotices()).thenReturn(testNotices);

        // NoticeController의 getAllNotices 메서드 호출
        ResponseEntity<List<Notice>> responseEntity = noticeController.getAllNotices();

        // HTTP 상태 코드 확인
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // 응답 본문에서 공지사항 리스트 확인
        List<Notice> retrievedNotices = responseEntity.getBody();
        assertEquals(2, retrievedNotices.size());
        assertEquals("Test Notice 1", retrievedNotices.get(0).getTitle());
        assertEquals("Test Notice 2", retrievedNotices.get(1).getTitle());
    }

    @Test
    public void testGetNoticeById() {
        // 테스트용 공지사항 객체 생성
        Notice testNotice = new Notice();
        testNotice.setNoticeId(1L);
        testNotice.setTitle("Test Notice");
        testNotice.setContent("This is a test notice");

        // NoticeService의 getNoticeById 메서드가 호출될 때 테스트용 객체 반환하도록 설정
        when(noticeService.getNoticeById(1L)).thenReturn(Optional.of(testNotice));

        // NoticeController의 getNoticeById 메서드 호출
        ResponseEntity<Notice> responseEntity = noticeController.getNoticeById(1L);

        // 응답 코드 확인
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // 응답 본문 확인
        assertEquals(testNotice, responseEntity.getBody());
        // NoticeService의 getNoticeById 메서드가 한 번 호출되었는지 확인
        verify(noticeService, times(1)).getNoticeById(1L);
    }

    @Test
    public void testUpdateNotice() {
        // 테스트용 공지사항 객체 생성
        Notice originalNotice = new Notice();
        originalNotice.setNoticeId(1L);
        originalNotice.setTitle("Original Notice");
        originalNotice.setContent("This is the original notice");

        // 업데이트할 새로운 공지사항 객체 생성
        Notice updatedNotice = new Notice();
        updatedNotice.setNoticeId(2L); //
        updatedNotice.setTitle("Updated Notice");
        updatedNotice.setContent("This is the updated notice");

        // NoticeService의 updateNotice 메서드가 호출될 때 테스트용 객체 반환하도록 설정
        when(noticeService.updateNotice(1L, updatedNotice)).thenReturn(updatedNotice);

        // NoticeController의 updateNotice 메서드 호출
        ResponseEntity<Notice> responseEntity = noticeController.updateNotice(1L, updatedNotice);

        // 응답 코드 확인
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // 응답 본문 확인
        assertEquals(updatedNotice, responseEntity.getBody());
        // NoticeService의 updateNotice 메서드가 한 번 호출되었는지 확인
        verify(noticeService, times(1)).updateNotice(1L, updatedNotice);
    }

    @Test
    public void testDeleteNotice() {
        // NoticeController의 deleteNotice 메서드 호출
        ResponseEntity<String> responseEntity = noticeController.deleteNotice(1L);

        // 응답 코드 확인
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        // NoticeService의 deleteNotice 메서드가 한 번 호출되었는지 확인
        verify(noticeService, times(1)).deleteNotice(1L);
    }
}
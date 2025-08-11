package com.example.project_cnw.service.teacher;

import com.example.project_cnw.dto.request.student.*;
import com.example.project_cnw.dto.request.teacher.CreateNoticeRequestDto;
import com.example.project_cnw.dto.request.teacher.SubjectApplicationRequestDto;
import com.example.project_cnw.dto.response.common.NoticeDetailResponseDto;
import com.example.project_cnw.dto.response.teacher.*;

public interface TeacherService {
    TeacherProfileResponseDto getProfile();
    void updateProfile(UpdateProfileRequestDto request);
    TeacherDashboardResponseDto getDashboardStats();
    SubjectMasterListResponseDto getSubjectMasters();
    void applyForSubject(SubjectApplicationRequestDto request);
    MySubjectsResponseDto getMySubjects();
    MyLecturesResponseDto getMyLectures();
    com.example.project_cnw.dto.response.teacher.NoticeListResponseDto getNotices(String title, org.springframework.data.domain.Pageable pageable);
    void createNotice(CreateNoticeRequestDto request);
    void updateNotice(Long noticeId, com.example.project_cnw.dto.request.teacher.UpdateNoticeRequestDto request);
    void deleteNotice(Long noticeId);
    NoticeDetailResponseDto getNoticeDetail(Long noticeId);

}

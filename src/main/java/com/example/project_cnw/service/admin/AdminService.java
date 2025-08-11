package com.example.project_cnw.service.admin;

import com.example.project_cnw.dto.request.admin.*;
import com.example.project_cnw.dto.response.admin.*;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    AdminProfileResponseDto getProfile();
    void updateProfile(AdminUpdateProfileRequestDto request);
    void completeSetup(AdminSetupRequestDto request);
    UserListResponseDto getUsers(String role, String status, String name, Pageable pageable);
    void approveUser(Long userId);
    void rejectUser(Long userId);
    UserDetailResponseDto getUserDetail(Long userId);
    SubjectListResponseDto getSubjects(String status, String subjectName, String teacherName, Pageable pageable);
    void approveSubject(Long subjectId);
    void rejectSubject(Long subjectId);
    LectureListResponseDto getLectures(String grade, String subjectName, String teacherName, Pageable pageable);
    void createLecture(CreateLectureRequestDto request);
    LectureDetailResponseDto getLectureDetail(Long lectureId);
    NoticeListResponseDto getNotices(String title, Pageable pageable);
    void createNotice(CreateNoticeRequestDto request);
    void updateNotice(Long noticeId, UpdateNoticeRequestDto request);
    void deleteNotice(Long noticeId);
    InquiryListResponseDto getInquiries(String status, Pageable pageable);;
    void updateInquiryStatus(Long inquiryId, UpdateInquiryStatusRequestDto request);
    AdminDashboardStatsResponseDto getDashboardStatus();
}

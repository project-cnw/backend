package com.example.project_cnw.service;

import com.example.project_cnw.dto.request.student.*;
import com.example.project_cnw.dto.response.common.NoticeDetailResponseDto;
import com.example.project_cnw.dto.response.student.*;

public interface StudentService {
    StudentProfileResponseDto getProfile();
    void updateProfile(UpdateProfileRequestDto request);
    StudentDashboardResponseDto getDashboardStats();
    TimetableResponseDto getTimetable(String academicYear, String semester, String grade,
                                             String affiliation, String subjectType, String subjectName,
                                             String teacherName, String subjectCode, org.springframework.data.domain.Pageable pageable);
    CourseRegistrationListResponseDto getCourseRegistrationList();
    void addToCart(AddToCartRequestDto request);
    void removeFromCart(Long lectureId);
    void bulkCourseRegistration(BulkCourseRegistrationRequestDto request);
    MyCourseResponseDto getMyCourses();
    void cancelCourseRegistration(Long registrationId);
    CourseHistoryResponseDto getCourseHistory(Integer academicYear, String semester);
    com.example.project_cnw.dto.response.student.NoticeListResponseDto getNotices(String title, org.springframework.data.domain.Pageable pageable);
    NoticeDetailResponseDto getNoticeDetail(Long noticeId);
}

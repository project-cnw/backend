package com.example.project_cnw.controller;

import com.example.project_cnw.common.ApiMappingPattern;
import com.example.project_cnw.common.ResponseDto;
import com.example.project_cnw.dto.request.admin.AdminSetupRequestDto;
import com.example.project_cnw.dto.request.admin.CreateNoticeRequestDto;
import com.example.project_cnw.dto.request.admin.UpdateInquiryStatusRequestDto;
import com.example.project_cnw.dto.request.admin.UpdateNoticeRequestDto;
import com.example.project_cnw.dto.response.admin.*;
import com.example.project_cnw.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping(ApiMappingPattern.API_ADMIN)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "관리자", description = "관리자 전용 API")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/profile")
    @Operation(summary = "관리자 프로필 조회", description = "관리자 프로필 정보를 조회합니다.")
    public ResponseEntity<ResponseDto> getProfile() {
        AdminProfileResponseDto response = adminService.getProfile();
        return ResponseEntity.ok(ResponseDto.success("관리자 프로필을 조회했습니다.", response));
    }

    @PutMapping("/profile")
    @Operation(summary = "관리자 프로필 수정", description = "관리자 프로필 정보를 수정합니다.")
    public ResponseEntity<ResponseDto> updateProfile(@Valid @RequestBody AdminUpdateProfileRequesstDto request) {
        adminService.updateProfile(request);
        return ResponseEntity.ok(ResponseDto.success("관리자 프로필을 조회했습니다.", response));
    }

    @PostMapping("/complete-setup")
    @Operation(summary = "관리자 초기 설정 완료", description = "관리자 최초 로그인 시 개인정보를 입력합니다.")
    public ResponseEntity<ResponseDto> completeSetup(@Valid @RequestBody AdminSetupRequestDto request) {
        adminService.updateProfile(request);
        return ResponseEntity.ok(ResponseDto.success("관리자 설정이 완료되었습니다."));
    }

    @GetMapping("/users")
    @Operation(summary = "사용자 목록 조회", description = "학생과 교사 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String name,
            @PageableDefault(size = 20) Pageable pageable) {
        UserListResponseDto response = adminService.getUsers(role, status, name, pageable);
        return ResponseEntity.ok(ResponseDto.success("사용자 목록을 조회했습니다.", response));
    }

    @PatchMapping("/users/{userId}/approve")
    @Operation(summary = "사용자 승인", description = "대기중인 사용자를 승인합니다.")
    public ResponseEntity<ResponseDto> approveUser(@PathVariable Long userId) {
        adminService.approveUser(userId);
        return ResponseEntity.ok(ResponseDto.success("사용자가 승인되었습니다."));
    }

    @PatchMapping("/users/{userId}/reject")
    @Operation(summary = "사용자 거절", description = "대기중인 사용자를 거절합니다.")
    public ResponseEntity<ResponseDto> rejectUser(@PathVariable Long userId) {
        adminService.rejectUser(userId);
        return ResponseEntity.ok(ResponseDto.success("사용자가 거절되었습니다."));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "사용자 상세 조회", description = "특정 사용자의 상세 정보를 조회합니다.")
    public ResponseEntity<ResponseDto> getUserDetail(@PathVariable Long userId) {
        UserDetailResponseDto response = adminService.getUserDetail(userId);
        return ResponseEntity.ok(ResponseDto.success("사용자 정보를 조회했습니다.", response));
    }

    @GetMapping("/subjects")
    @Operation(summary = "과목 신청 목록 조회", description = "교사들이 신청한 과목 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getSubjects(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String teacherName,
            @PageableDefault(size = 20) Pageable pageable) {
        subjectListResponseDto response = adminService.getSubjects(status, subjectName, teacherName, pageable);
        return ResponseEntity.ok(ResponseDto.success("과목 목록을 조회했습니다.", response));
    }

    @PatchMapping("/subjects/{subjectId}/approve")
    @Operation(summary = "과목 승인", description = "교사가 신청한 과목을 승인합니다.")
    public ResponseEntity<ResponseDto> approveSubject(@PathVariable Long subjectId) {
        adminService.approveSubject(subjectId);
        return ResponseEntity.ok(ResponseDto.success("과목이 승인되었습니다."));
    }

    @PatchMapping("/subjects/{subjectId/reject}")
    @Operation(summary = "과목 거절", description = "교사가 신청한 과목을 거절합니다.")
    public ResponseEntity<ResponseDto> rejectSubject(@PathVariable Long subjectId) {
        adminService.rejectSubject(subjectId);
        return ResponseEntity.ok(ResponseDto.success("과목이 거절되었습니다."));
    }

    @GetMapping("/lecture")
    @Operation(summary = "강의 목록 조회", description = "개설된 강의 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getLectures(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String teacherName,
            @PageableDefault(size = 20) Pageable pageable) {
        LectureListResponseDto response = adminService.getLectures(grade, subjectName, teacherName, pageable);
        return ResponseEntity.ok(ResponseDto.success("강의 목록을 조회했습니다.", response));
    }

    @PostMapping("/lectures")
    @Operation(summary = "강의 개설", description = "승인된 과목으로 강의를 개설합니다.")
    public ResponseEntity<ResponseDto> createLecture(@Valid @RequestBody CreateLectureRequestDto request) {
        adminService.createLecture(request);
        return ResponseEntity.ok(ResponseDto.success("강의가 개설되었습니다."));
    }

    @GetMapping("/lectures/{lectureId}")
    @Operation(summary = "강의 상세 조회", description = "특정 강의의 상세 정보를 조회합니다.")
    public ResponseEntity<ResponseDto> getLectureDetail(@PathVariable Long lectureId) {
        LectureDetailResponseDto response = adminService.getLectureDetail(lectureId);
        return ResponseEntity.ok(ResponseDto.success("강의 정보를 조회했습니다.", response));
    }

    @GetMapping("/notices")
    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getNotices(
            @RequestParam(required = false) String title,
            @PageableDefault(size = 20) Pageable) {
        NoticeListResponseDto response = adminService.getNotices(title, pageable);
        return ResponseEntity.ok(ResponseDto.success("공지사항 목록을 조회했습니다.", response));
    }

    @PostMapping("/notices")
    @Operation(summary = "공지사항 작성", description = "새로운 공지사항을 작성합니다.")
    public ResponseEntity<ResponseDto> createNotice(@Valid @RequestBody CreateNoticeRequestDto request) {
        adminService.createNotice(request);
        return ResponseEntity.ok(ResponseDto.success("공지사항이 작성되었습니다."));
    }

    @PutMapping("/notices/{noticeId}")
    @Operation(summary = "공지사항 수정", description = "기존 공지사항을 수정합니다.")
    public ResponseEntity<ResponseDto> updateNotice(@PathVariable Long noticeId, @Valid @RequestBody UpdateNoticeRequestDto request) {
        adminService.updateNotice(noticeId, request);
        return ResponseEntity.ok(ResponseDto.success("공지사항이 수정되었습니다."));
    }

    @DeleteMapping("/notices/{noticeId}")
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteNotice(@PathVariable Long noticeId) {
        adminService.deleteNotice(noticeId);
        return ResponseEntity.ok(ResponseDto.success("공지사항이 삭제되었습니다."));
    }

    @GetMapping("/inquiries")
    @Operation(summary = "문의사항 목록 조회", description = "문의사항 목록 조회합니다.")
    public ResponseEntity<ResponseDto> getInquires(
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable) {
        InquiryListResponseDto response = adminService.getInquiries(status, pageable);
        return ResponseEntity.ok(ResponseDto.success("문의사항 상태가 변경되었습니다.", response));
    }

    @PatchMapping("/inquiries/{inquiryId}/status")
    @Operation(summary = "문의사항 상태 변경", description = "문의사항의 처리 상태를 변경합니다.")
    public ResponseEntity<ResponseDto> updateInquiryStatus(@PathVariable Long inquiryId, @Valid @RequestBody UpdateInquiryStatusRequestDto request) {
        adminService.updateInquiryStatus(inquiryId, request);
        return ResponseEntity.ok(ResponseDto.success("문의사항 상태가 변경되었습니다."));
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "대시보드 통계 조회", description = "관리자 대시보드용 통계 데이터를 조회합니다.")
    public ResponseEntity<ResponseDto> getDashboardStats() {
        AdminDashboardStatsResponseDto response = adminService.getDashboardStats();
        return ResponseEntity.ok(ResponseDto.success("대시보드 통계를 조회했습니다.", response));
    }

}

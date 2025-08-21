package com.example.project_cnw.controller;

import com.example.project_cnw.common.ApiMappingPattern;
import com.example.project_cnw.common.ResponseDto;
import com.example.project_cnw.dto.request.teacher.CreateNoticeRequestDto;
import com.example.project_cnw.dto.request.teacher.UpdateProfileRequestDto;
import com.example.project_cnw.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.API_TEACHER)
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
@Tag(name = "교사", description = "교사 전용 API")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/profile")
    @Operation(summary = "교사 프로필 조회", description = "교사 프로필 정보 조회합니다.")
    public ResponseEntity<ResponseDto> getProfile() {
        var response = teacherService.getProfile();
        return ResponseEntity.ok(ResponseDto.success("교사 프로필을 조회했습니다.", response));
    }

    @PutMapping("/profile")
    @Operation(summary = "교사 프로필 수정", description = "교사 프로필 정보를 수정합니다.")
    public ResponseEntity<ResponseDto> updateProfile(@Valid @RequestBody UpdateProfileRequestDto request) {
        teacherService.updateProfile(request);
        return ResponseEntity.ok(ResponseDto.success("프로필이 수정되었습니다."));
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "교사 대시보드 조회", description = "교사 대시보드 통계를 조회합니다.")
    public ResponseEntity<ResponseDto> getDashboardStats() {
        var response = teacherService.getDashboardStats();
        return ResponseEntity.ok(ResponseDto.success("대시보드 정보를 조회했습니다.", response));
    }

    @GetMapping("/subject-masters")
    @Operation(summary = "과목 마스터 목록 조회", description = "신청 가능한 과목 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getSubjectMasters() {
        var response = teacherService.getSubjectMasters();
        return ResponseEntity.ok(ResponseDto.success("과목 목록을 조회했습니다.", response));
    }

    @PostMapping("/subjects/apply")
    @Operation(summary = "과목 신청", description = "새로운 과목을 신청합니다.")
    public ResponseEntity<ResponseDto> applyForSubject(@Valid @RequestBody subjectApplicationRequestDto request) {
        teacherService.applyForSubject(request);
        return ResponseEntity.ok(ResponseDto.success("과목 신청이 완료되었습니다."));
    }

    @GetMapping("/my-subjects")
    @Operation(summary = "내 신청 과목 조회", description = "신청한 과목 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getMySubjects() {
        var response = teacherService.getMySubjects();
        return ResponseEntity.ok(ResponseDto.success("신청 과목을 조회했습니다.", response));
    }

    @GetMapping("/my-lectures")
    @Operation(summary = "내 강의 목록 조회", description = "담당 강의 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getMyLectures() {
        var response = teacherService.getMyLectures();
        return ResponseEntity.ok(ResponseDto.success("담당 강의를 조회했습니다.", response));
    }

    @GetMapping("/notices")
    @Operation(summary = "공지사항 목록 조회", description = "교사 대상 공지사항을 조회합니다.")
    public ResponseEntity<ResponseDto> getNotices(
            @RequestParam(required = false) String title,
            @PageableDefault(size = 20) Pageable pageable) {
        var response = teacherService.getNotices(title, pageable);
        return ResponseEntity.ok(ResponseDto.success("공지사항을 조회했습니다.", response));
    }

    @PostMapping("/notices")
    @Operation(summary = "공지사항 작성", description = "새로운 공지사항을 작성합니다.")
    public ResponseEntity<ResponseDto> createNotice(@Valid @RequestBody CreateNoticeRequestDto requset) {
        teacherService.createNotice(requset);
        return ResponseEntity.ok(ResponseDto.success("공지사항이 작성되었습니다."));
    }

    @PutMapping("/notices/{noticeId}")
    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    public ResponseEntity<ResponseDto> updateNotice(@PathVariable Long noticeId, @Valid @RequestBody com.example.project_cnw.dto.request.teacher.UpdateNoticeRequestDto request) {
        teacherService.updateNotice(noticeId, request);
        return ResponseEntity.ok(ResponseDto.success("공지사항이 수정되었습니다."));
    }

    @DeleteMapping("/notices/{noticeId}")
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteNotice(@PathVariable Long noticeId) {
        teacherService.deleteNotice(noticeId);
        return ResponseEntity.ok(ResponseDto.success("공지사항이 삭제되었습니다."));
    }

    @GetMapping("/notices/{noticeId}")
    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세 내용을 조회합니다.")
    public ResponseEntity<ResponseDto> getNoticeDetail(@PathVariable Long noticeId) {
        var response = teacherService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(ResponseDto.success("공지사항 상세 정보를 조회했습니다.", response));
    }







}

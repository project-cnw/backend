package com.example.project_cnw.controller;

import com.example.project_cnw.common.ApiMappingPattern;
import com.example.project_cnw.common.ResponseDto;
import com.example.project_cnw.dto.request.student.AddToCartRequestDto;
import com.example.project_cnw.dto.request.student.BulkCourseRegistrationRequestDto;
import com.example.project_cnw.dto.request.student.UpdateProfileRequestDto;
import com.example.project_cnw.service.StudentService;
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
@RequestMapping(ApiMappingPattern.API_STUDENT)
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
@Tag(name = "학생", description = "학생 전용 API")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/profile")
    @Operation(summary = "학생 프로필 조회", description = "학생 프로필 정보를 조회합니다.")
    public ResponseEntity<ResponseDto> getProfile() {
        var response = studentService.getProfile();
        return ResponseEntity.ok(ResponseDto.success("학생 프로필을 조회했습니다.", response));
    }

    @PutMapping("/profile")
    @Operation(summary = "학생 프로필 수정", description = "학생 프로필 정보를 수정합니다.")
    public ResponseEntity<ResponseDto> updateProfile(@Valid @RequestBody UpdateProfileRequestDto request) {
        studentService.updateProfile(request);
        return ResponseEntity.ok(ResponseDto.success("프로필이 수정되었습니다."));
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "학생 대시보드 조회", description = "학생 대시보드 통계를 조회합니다.")
    public ResponseEntity<ResponseDto> getDashboardStats() {
        var response = studentService.getDashboardStats();
        return ResponseEntity.ok(ResponseDto.success("대시보드 정보를 조회했습니다.", response));
    }

    @GetMapping("/timetable")
    @Operation(summary = "과목시간표 조회", description = "과목시간표를 조회합니다.")
    public ResponseEntity<ResponseDto> getTimetable(
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String affiliation,
            @RequestParam(required = false) String subjectType,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) String subjectCode,
            @PageableDefault(size = 20) Pageable pageable) {
        var response = studentService.getTimetable(academicYear, semester, grade, affiliation,
                subjectType, subjectName, teacherName, subjectCode, pageable);
        return ResponseEntity.ok(ResponseDto.success("시간표를 조회했습닌다.", response));
    }

    @GetMapping("/course-registration")
    @Operation(summary = "수강신청 페이지 조회", description = "수강신청 가능한 강의 목록과 장바구니를 조회합니다.")
    public ResponseEntity<ResponseDto> getCourseRegistrationList() {
        var response = studentService.getCourseRegistrationList();
        return ResponseEntity.ok(ResponseDto.success("수강신청 목록을 조회했습니다.", response));
    }

    @PostMapping("/course-registration/cart")
    @Operation(summary = "장바구니 추가", description = "강의를 장바구니에 추가합니다.")
    public ResponseEntity<ResponseDto> addToCart(@Valid @RequestBody AddToCartRequestDto request) {
        studentService.addToCart(request);
        return ResponseEntity.ok(ResponseDto.success("장바구니에 추가되었습니다."));
    }

    @DeleteMapping("/course-registration/cart/{lectureId}")
    @Operation(summary = "장바구니 삭제", description = "장바구니에서 강의를 삭제합니다.")
    public ResponseEntity<ResponseDto> removeFromCart(@PathVariable Long lectureId) {
        studentService.removeFromCart(lectureId);
        return ResponseEntity.ok(ResponseDto.success("장바구니에서 삭제되었습니다."));
    }

    @PostMapping("/course-registration/apply")
    @Operation(summary = "일괄 수강신청", description = "장바구니의 강의들을 일괄 신청합니다.")
    public ResponseEntity<ResponseDto> bulkCourseRegistration(@Valid @RequestBody BulkCourseRegistrationRequestDto request) {
        studentService.bulkCourseRegistration(request);
        return ResponseEntity.ok(ResponseDto.success("수강신청이 완료되었습니다."));
    }

    @GetMapping("/my-courses")
    @Operation(summary = "수강신청 내역 조회", description = "본인의 수강신청 내역을 조회합니다.")
    public ResponseEntity<ResponseDto> getMyCourses() {
        var response = studentService.getMyCourses();
        return ResponseEntity.ok(ResponseDto.success("수강신청 내역을 조회했습니다.", response));
    }

    @DeleteMapping("/my-courses/{registrationId}")
    @Operation(summary = "수강신청 취소", description = "수강신청을 취소합니다.")
    public ResponseEntity<ResponseDto> cancelCourseRegistration(@PathVariable Long registrationId) {
        studentService.cancelCourseRegistration(registrationId);
        return ResponseEntity.ok(ResponseDto.success("수강신청이 취소되었습니다."));
    }

    @GetMapping("/course-history")
    @Operation(summary = "수강이력 조회", description = "과거 수강이력을 조회합니다.")
    public ResponseEntity<ResponseDto> getCourseHistory(
            @RequestParam(required = false) Integer academicYear,
            @RequestParam(required = false) String semester) {
        var response = studentService.getCourseHistory(academicYear, semester);
        return ResponseEntity.ok(ResponseDto.success("수강이력을 조회했습니다.", response));
    }

    @GetMapping("/notices")
    @Operation(summary = "공지사항 목록 조회", description = "학생 대상 공지사항을 조회합니다.")
    public ResponseEntity<ResponseDto> getNotices(
            @RequestParam(required = false) String title,
            @PageableDefault(size = 20) Pageable pageable) {
        var response = studentService.getNotices(title, pageable);
        return ResponseEntity.ok(ResponseDto.success("공지사항을 조회했습니다.", response));
    }

    @GetMapping("/notices/{noticeId}")
    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세 내용을 조회합니다.")
    public ResponseEntity<ResponseDto> getNoticeDetail(@PathVariable Long noticeId) {
        var response = studentService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(ResponseDto.success("공지사항 상세 정보를 조회했습니다.", response));
    }
}

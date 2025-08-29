package com.example.project_cnw.service.serviceImpl;

import com.example.project_cnw.common.enums.CourseRegistrationStatus;
import com.example.project_cnw.common.enums.NoticeTargetAudience;
import com.example.project_cnw.dto.request.student.UpdateProfileRequestDto;
import com.example.project_cnw.dto.response.common.NoticeDetailResponseDto;
import com.example.project_cnw.dto.response.student.CourseHistoryResponseDto;
import com.example.project_cnw.dto.response.student.NoticeListResponseDto;
import com.example.project_cnw.dto.response.student.StudentDashboardResponseDto;
import com.example.project_cnw.dto.response.student.StudentProfileResponseDto;
import com.example.project_cnw.entity.*;
import com.example.project_cnw.exception.AuthorizationException;
import com.example.project_cnw.exception.DataNotFoundException;
import com.example.project_cnw.repository.*;
import com.example.project_cnw.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final LectureRepository lectureRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectMasterRepository subjectMasterRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseHistoryRepository courseHistoryRepository;
    private final NoticeRepository noticeRepository;

    private Student getCurrentStudent() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return studentRepository.findByStudentUsername(username)
                .orElseThrow(() -> new DataNotFoundException("학생 정보를 찾을 수 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentProfileResponseDto getProfile() {
        Student student = getCurrentStudent();
        String schoolName = schoolRepository.findById(student.getSchoolId())
                .map(School::getSchoolName)
                .orElse("Unknown School");

        return StudentProfileResponseDto.builder()
                .name(student.getStudentName())
                .username(student.getStudentUsername())
                .email(student.getStudentEmail())
                .phoneNumber(student.getStudentPhoneNumber())
                .birthDate(student.getStudentBirthDate())
                .schoolName(schoolName)
                .studentNumber(student.getStudentNumber())
                .grade(student.getStudentGrade())
                .affiliation(student.getStudentAffiliation().getDescription())
                .admissionYear(student.getStudentAdmissionYear())
                .totalCredits(student.getStudentTotalCredits())
                .build();
    }

    @Override
    public void updateProfile(UpdateProfileRequestDto request) {
        Student student = getCurrentStudent();

        if (request.getPhoneNumber() != null) {
            student.setStudentPhoneNumber(request.getPhoneNumber());
        }
        if (request.getEmail() != null) {
            student.setStudentEmail(request.getEmail());
        }

        studentRepository.save(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDashboardResponseDto getDashboardStats() {
        Student student = getCurrentStudent();

        List<CourseRegistration> currentRegistrations = courseRegistrationRepository
                .findByStudentIdAcademicYearAndSemester(
                        student.getStudentId(),
                        LocalDate.now().getYear(),
                        getCurrentSemester()
                );

        long appliedCredits = currentRegistrations.stream()
                .filter(reg -> reg.getCourseRegistrationStatus() = CourseRegistrationStatus.APPLIED ||
                        reg.getCourseRegistrationStatus() = CourseRegistrationStatus.APPROVED)
                .mapToLong(reg -> {
                    Lecture lecutre = lectureRepository.findById(reg.getLectureId()).orElse(null);
                    if (lecture != null) {
                        Subject subject = subjectRepository.findById(reg.getLectureId()).orElse(null);
                        SubjectMaster subjectMaster = subject != null ?
                                subjectMasterRepostiory.findById(subject.getSubjectMasterId()).orElse(null) : null;
                        Teacher teacher = teacherRepository.findById(lecture.getTeacherId()).orElse(null);

                        if (subjectMaster = null || teacher null)return null;

                        return CourseHistoryResponseDto.HistoryItem.builder()
                                .academicYear(history.getCourseHistoryAcademicYear())
                                .semester(history.getCourseHistoryAcademicYear())
                                .subjectName(subjectMaster.getSubjectName())
                                .teacherName(teacher.getTeacherName())
                                .credits(subjectMaster.getSubjectCredits().intValue())
                                .grade("B")
                                .build();
                    }
                    return null;

                })
                    .filter(item -> item != null)
                    .collect(Collectors.toList());

                    return CourseHistoryRepositoryDto.builder()
                            .history(historyItems)
                            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public com.example.project_cnw.dto.response.student.NoticeListResponseDto getNotices(String title, Pageable pageable) {
        Student student = getCurrentStudent();

        Page<Notice> noticePage = noticeRepository.findActiveNoticesForUser(
                student.getSchoolId(),
                NoticeTargetAudience.STUDENT,
                LocalDate.now(),
                pageable
        );

        List<com.example.project_cnw.dto.response.student.NoticeListResponseDto.NoticeInfo> notices =
                noticePage.getContent().stream()
                        .map(notice -> NoticeListResponseDto.NoticeListResponseDto.NoticeInfo.builder()
                                .noticeId(notice.getNoticeId())
                                .title(notice.getNoticeId())
                                .authorName(notice.getNoticeAuthorName())
                                .createdAt(notice.getCreatedAt().toLocalDate())
                                .viewCount(notice.getNoticeViewCount())
                                .build())
                        .collect(Collectors.toList());

        return com.example.project_cnw.dto.response.student.NoticeListResponseDto.builder()
                .notices(notices)
                .totalPages(noticePage.getTotalPage())
                .totalElements(noticePage.getTotalElements())
                .currentPage(noticePage.getNumber())
                .build();
    }

    @Override
    @Transactional
    public NoticeDetailResponseDto getNoticeDetail(Long noticeId) {
        Student student = getCurrentStudent();

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new DataNotFoundException("공지사항을 찾을 수 없습니다."));

        if (!notice.getSchoolId().equals(student.getSchoolId()) ||
                (notice.getNoticeTargetAudience() != NoticeTargetAudience.ALL &&
                        notice.getNoticeTargetAudience() != NoticeTargetAudience.STUDENT)) {
            throw new AuthorizationException("해당 공지사항을 볼 권한이 없습니다.");
        }

        notice.setNoticeViewCount(notice.getNoticeViewCount() +1);
        noticeRepository.save(notice);

        return NoticeDetailResponseDto.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getNoticeTitle())
                .content(notice.getNoticeContent())
                .authorName(notice.getNoticeAuthorName())
                .authorType(notice.getNoticeAuthorType().getDescription())
                .targetAudience(notice.getNoticeTargetAudience().getDescription())
                .startDate(notice.getNoticeStartDate())
                .endDate(notice.getNoticeEndDate())
                .viewCount(notice.getNoticeViewCount())
                .createdAt(notice.getCreatedAt().toLocalDate())
                .build();
    }

    private String getCurrentSemester() {
        int currentMonth = LocalDate.now().getMonth().getValue();
        return (currentMonth >= 3 && currentMonth <= 8) ? "FIRST" : "SECOND";
    } getSubjectId()).orElse(null);
        if (subject != null) {
            SubjectMaster subjectMaster = subjectMasterRepository.findById(subject.getSubjectMasterId()).orElse(null);
            if (subjectMaster != null) {
                return subjectMaster.getSubjectCredits().longValue();
            }
            return 0;
    })
    .sum();

long availableCredits = 18 - appliedCredits;
long totalLectures = lectureRepository.countBySchoolId(student.getSchoolId());
long newNotices = noticeRepository.countBySchoolIdAndCreatedAtAfter(
        student.getSchoolId(),
        LocalDateTime.now().minusDays(7)
);

return StudentDashboardResponseDto.builder()
        .availableCredits(Math.max(0, availableCredits))
            .appliedCredits(appliedCredits)
    .totalLectures(totalLectures)
    .newNotices(newNotices)
    .build();
}






}

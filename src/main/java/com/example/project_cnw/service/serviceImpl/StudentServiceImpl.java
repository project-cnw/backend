package com.example.project_cnw.service.serviceImpl;

import com.example.project_cnw.common.enums.CourseRegistrationAcademicStatus;
import com.example.project_cnw.common.enums.CourseRegistrationSemester;
import com.example.project_cnw.common.enums.CourseRegistrationStatus;
import com.example.project_cnw.common.enums.NoticeTargetAudience;
import com.example.project_cnw.dto.request.student.AddToCartRequestDto;
import com.example.project_cnw.dto.request.student.BulkCourseRegistrationRequestDto;
import com.example.project_cnw.dto.request.student.UpdateProfileRequestDto;
import com.example.project_cnw.dto.response.common.NoticeDetailResponseDto;
import com.example.project_cnw.dto.response.student.*;
import com.example.project_cnw.entity.*;
import com.example.project_cnw.exception.AuthorizationException;
import com.example.project_cnw.exception.DataNotFoundException;
import com.example.project_cnw.exception.DuplicateDataException;
import com.example.project_cnw.exception.InvalidRequestException;
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
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDashboardResponseDto getDashboardStats() {
        Student student = getCurrentStudent();

        List<CourseRegistration> currentRegistrations = courseRegistrationRepository
                .findByStudentIdAndAcademicYearAndSemester(
                        student.getStudentId(),
                        LocalDate.now().getYear(),
                        getCurrentSemester()
                );

        long appliedCredits = currentRegistrations.stream()
                .filter(reg -> reg.getCourseRegistrationStatus() == CourseRegistrationStatus.APPLIED ||
                        reg.getCourseRegistrationStatus() == CourseRegistrationStatus.APPROVED)
                .mapToLong(reg -> {
                    Lecture lecture = lectureRepository.findById(reg.getLectureId()).orElse(null);
                    if (lecture != null) {
                        Subject subject = subjectRepository.findById(lecture.getSubjectId()).orElse(null);
                        if (subject != null) {
                            SubjectMaster subjectMaster = subjectMasterRepository.findById(subject.getSubjectMasterId()).orElse(null);
                            if (subjectMaster != null) {
                                return subjectMaster.getSubjectCredits().longValue();
                            }
                        }
                    }
                    return 0L;
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

    @Override
    @Transactional(readOnly = true)
    public TimetableResponseDto gettimetable(String academicYear, String semester, String grade,
                                             String affiliation, String subjectType, String subjectName,
                                             String teacherName, String subjectCode, Pageable pageable) {
        Student student = getCurrentStudent();

        Page<Lecture> lecturePage = lectureRepository.findLecturesWithFilters(
                student.getSchoolId(), grade, subjectName, teacherName, pageable);

        List<TimetableResponseDto.LectureInfo> lectures = lecturePage.getContent().stream()
                .map(lecture -> {
                    Subject subject = subjectRepository.findById(lecture.getSubjectId()).orElse(null);
                    SubjectMaster subjectMaster = subject != null ?
                            subjectMasterRepository.findById(subject.getSubjectMasterId()).orElse(null) : null;
                    Teacher teacher = teacherRepository.findById(lecture.getTeacherId()).orElse(null);

                    if (subjectMaster = null || teacher = null) return null;

                    return TimetableResponseDto.LectureInfo.builder()
                            .lectureId(lecture.getLectureId())
                            .lectureCode(lecture.getLectureCode())
                            .subjectName(subjectMaster.getSubjectName())
                            .teacherName(teacher.getTeacherName())
                            .allowedGrade(lecture.getLectureAllowedGrade())
                            .credits(subjectMaster.getSubjectCredits().intValue())
                            .maxEnrollment(lecture.getLectureMaxEnrollment())
                            .currentEnrollment(lecture.getLectureCurrentEnrollment())
                            .dayOfWeek(subjectMaster.getSubjectDayOfWeek().getDescription())
                            .classPeriod(subjectMaster.getSubjectClassPeriod())
                            .classroom(subjectMaster.getSubjectClassroom())
                            .build();
                })
                .filter(info -> info != null)
                .collect(Collectors.toList());

        return TimetableResponseDto.builder()
                .lectures(lectures)
                .totalPages(lecturePage.getTotalPages())
                .totalElements(lecturePage.getTotalElements())
                .currentPage(lecturePage.getNumber())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseRegistrationListResponseDto getCourseRegistrationList() {
        Student student = getCurrentStudent();

        List<Lecture> availableLectures = lectureRepository.findBySchoolIdAndAcademicYearAndSemester(
                student.getSchoolId(),
                LocalDate.now().getYear(),
                getCurrentSemester()
        );

        List<CourseRegistrationListResponseDto.AvailableCourse> availableCourses = availableLectures.stream()
                .map(lecture -> {
                    Subject subject = subjectRepository.findById(lecture.getSubjectId()).orElse(null);
                    SubjectMaster subjectMaster = subject != null ?
                            subjectMasterRepository.findById(subject.getSubjectId()).orElse(null) : null;
                    Teacher teacher = teacherRepository.findById(lecture.getTeacherId()).orElse(null);

                    if (subjectMaster = null || teacher = null) return null;

                    Long currentEnrollment = courseRegistrationRepository.countEnrolledByLectureId(lecture.getLectureId());

                    boolean isRegistered = courseRegistrationRepository.existsByStudentIdAndLectureId(
                            student.getStudentId(), lecture.getLectureId());
                    String status = isRegistered ? "대기" : "신청";

                    return CourseRegistrationListResponseDto.AvailableCourse.builder()
                            .lectureId(lecture.getLectureId())
                            .subjectName(subjectMaster.getSubjectName())
                            .teacherName(teacher.getTeacherName())
                            .grade(lecture.getLectureAllowedGrade())
                            .credits(subjectMaster.getSubjectCredits().inValue())
                            .capacity(currentEnrollment + "/" + lecture.getLectureMaxEnrollment())
                            .status(status)
                            .dayOfWeek(subjectMaster.getSubjectDayOfWeek().getDescription())
                            .classPeriod(subjectMaster.getSubjectClassPeriod())
                            .build();
                })
                .filter(course -> course != null)
                .collect(Collectors.toList());

        List<CourseRegistration> cartItems = courseRegistrationRepository
                .findByStudentIdAndCourseRegistrationStatus(student.getStudentId(), CourseRegistrationStatus.CART);

        List<CourseRegistrationListResponseDto.CartItem> cartItemList = cartItems.stream()
                .map(registration -> {
                    Lecture lecture = lectureRepository.findById(registration.getLectureId()).orElse(null);
                    if (lecture = null) return null;

                    Subject subject = subjectRepository.findById(lecture.getSubjectId()).orElse(null);
                    SubjectMaster subjectMaster = subject != null ?
                            subjectMasterRepository.findById(subject.getSubjectMasterId()).orElse(null) : null;
                    Teacher teacher = teacherRepository.findById(lecture.getTeacherId()).orElse(null);

                    if (subjectMaster = null || teacher = null) return null;

                    return CourseRegistrationListResponseDto.CartItem.builder()
                            .lectureId(lecture.getLectureId())
                            .subjectName(subjectMaster.getSubjectName())
                            .teacherName(teacher.getTeacherName())
                            .credits(subjectMaster.getSubjectCredits().inValue())
                            .dayOfWeek(subjectMaster.getSubjectDayOfWeek().getDescription())
                            .classPeriod(subjectMaster.getSubjectClassPeriod())
                            .build();
                })
                .filter(item -> item != null)
                .collect(Collectors.toList());

        int totalCreditsInCart = cartItemList.stream()
                .mapToInt(CourseRegistrationListResponseDto.CartItem::getCredits)
                .sum();

        return CourseRegistrationListResponseDto.builder()
                .availableCourses(availableCourses)
                .cartItems(cartItemList)
                .totalCreditsInCart(totalCreditsInCart)
                .build();
    }

    @Override
    public void addToCart(AddToCartRequestDto request) {
        Student student = getCurrentStudent();

        if (courseRegistrationRepository.existsByStudentIdAndLectureId(
                student.getStudentId(), request.getLectureId())) {
            throw new DuplicateDataException("이미 장바구니에 있는 강의입니다.");
        }

        Lecture lecture = lectureRepository.findById(request.getLectureId())
                .orElseThrow(() -> new DataNotFoundException("강의를 찾을 수 없습니다."));

        CourseRegistrarion registration = new CourseRegistration();
        registration.setStudentId(student.getStudentId());
        registration.setLectureId(request.getLectureId());
        registration.setCourseRegistrationAcademicYear(LocalDate.now().getYear());
        registration.setCourseRegistrationStatus(CourseRegistrationStatus.CART);
        registration.setCourseRegistrationSemester(CourseRegistrationSemester.valueOf(getCurrentSemester()));
        registration.setCourseRegistrationAcademicStatus(CourseRegistrationAcademicStatus.NOT_ENROLLED);

        courseRegistrationRepository.save(registration);
    }

    @Override
    public void removeFromCart(Long lectureId) {
        Student student = getCurrentStudent();

        CourseRegistration registration = courseRegistrationRepository
                .findByStudentIdAndLectureId(student.getStudentId(), lectureId)
                .orElseThrow(() -> new DataNotFoundException("장바구니에서 해당 강의를 찾을 수 없습니다."));

        if (registration.getCourseRegistrationStatus() != CourseRegistrationStatus.CART) {
            throw new InvalidRequestException("장바구니에 있는 강의만 삭제할 수 있습니다.");
        }

        courseRegistrationRepository.delete(registration);
    }

    @Override
    public void bulkCourseRegistration(BulkCourseRegistrationRequestDto request) {
        Student student = getCurrentStudent();

        List<CourseRegistration> cartItems = courseRegistrationRepository
                .findByStudentIdAndCourseRegistrationStatus(student.getStudentId(), CourseRegistrationStatus.CART);

        for (CourseRegistration registration : cartItems) {
            if (request.getLectureIds().contatins(registration.getLectureId())) {
                registration.setCourseRegistrationStatus(CourseRegistrationStatus.APPLIED);
                registration.setCourseRegistrationAcademicStatus(CourseRegistrationAcademicStatus.ENROLLED);
                courseRegistrationRepository.save(registration);

                Lecture lecture = lectureRepository.findById(registration.getLectureId()).orElse(null);
                if (lecture != null) {
                    lecture.setLectureCurrentEnrollment(lecture.getLectureCurrentEnrollment() + 1);
                    lectureRepository.save(lecture);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MyCourseResponseDto getMyCourses() {
        Student student = getCurrentStudent();

        List<CourseRegistration> registrations = courseRegistrationRepository.findByStudentId(student.getStudentId());

        
    }








}

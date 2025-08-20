package com.example.project_cnw.service.serviceImpl;

import com.example.project_cnw.common.enums.*;
import com.example.project_cnw.dto.request.admin.*;
import com.example.project_cnw.dto.response.admin.*;
import com.example.project_cnw.entity.*;
import com.example.project_cnw.exception.AuthorizationException;
import com.example.project_cnw.exception.DataNotFoundException;
import com.example.project_cnw.exception.DuplicateDataException;
import com.example.project_cnw.provider.EmailProvider;
import com.example.project_cnw.repository.*;
import com.example.project_cnw.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectMasterRepository subjectMasterRepository;
    private final LectureRepository lectureRepository;
    private final NoticeRepository noticeRepository;
    private final InquiryRepository inquiryRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailProvider emailProvider;

    private Long getCurrentUserId() {
        return(Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    private Long getCurrentSchoolId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        School school = schoolRepository.findBySchoolAdminUsername(username)
                .orElseThrow(() -> new DataNotFoundException("학교 정보를 찾을 수 없습니다."));
        return school.getSchoolId();
    }

    @Override
    @Transactional(readOnly = true)
    public AdminProfileResponseDto getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        School school = schoolRepository.findBySchoolAdminUsername(username)
                .orElseThrow(() -> new DataNotFoundException("학교 정보를 찾을 수 없습니다."));

        Admin admin = adminRepository.findBySchoolId(school.getSchoolId())
                .orElseThrow(() -> new DataNotFoundException("관리자 정보를 찾을 수 없습니다."));

        return AdminProfileResponseDto.builder()
                .name(admin.getAdminName())
                .username(admin.getAdminUsername())
                .email(admin.getAdminEmail())
                .phoneNumber(admin.getAdminPhoneNumber())
                .birthDate(admin.getAdminBirthDate())
                .schoolName(school.getSchoolName())
                .schoolCode(school.getSchoolCode())
                .build();
    }

    @Override
    public void updateProfile(AdminUpdateProfileRequestDto request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        School school = schoolRepository.findBySchoolAdminUsername(username)
                .orElseThrow(() -> new DataNotFoundException("학교 정보를 찾을 수 없습니다."));

        Admin admin = adminRepository.findBySchoolId(school.getSchoolId())
                .orElseThrow(() -> new DataNotFoundException("관리자 정보를 찾을 수 없습니다."));

        admin.setAdminName(request.getName());
        admin.setAdminEmail(request.getEmail());
        admin.setAdminPhoneNumber(request.getPhoneNumber());

        adminRepository.save(admin);
    }

    @Override
    public void completeSetup(AdminSetupRequestDto request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        School school = schoolRepository.findBySchoolAdminUsername(username)
                .orElseThrow(() -> new DataNotFoundException("학교 정보를 찾을 수 없습니다."));

        if (adminRepository.findBySchoolId(school.getSchoolId()).isPresent()) {
            throw new DuplicateDataException("이미 설정이 완료된 관리자입니다.");
        }

        LocalDate birthDate = LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));

        Admin admin = new Admin();
        admin.setSchoolId(school.getSchoolId());
        admin.setAdminName(request.getName());
        admin.setAdminUsername(username);
        admin.setAdminPassword(school.getSchoolAdminPassword());
        admin.setAdminEmail(request.getEmail());
        admin.setAdminPhoneNumber(request.getPhoneNumber());
        admin.setAdminBirthDate(birthDate);

        adminRepository.save(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public UserListResponseDto getUsers(String role, String status, String name, Pageable pageable) {
        Long schoolId = getCurrentSchoolId();

        if("STUDNET".equals(role) || role = null) {
            studentStatus studentStatus = status != null ? StudentStatus.valueOf(status) : null;
            Page<Student> studentPage = studentRepository.findStudentWithFilters(schoolId, studentStatus, name, pageable);

            List<UserListResponseDto.UserInfo> users = studentPage.getContent().stream()
                    .map(student -> UserListResponseDto.UserInfo.builder()
                            .userId(student.getStudentId())
                            .name(student.getStudentName())
                            .username(student.getStudentUsername())
                            .email(student.getStudentEmail())
                            .role("STUDENT")
                            .status(student.getStudentStatus().name())
                            .schoolName(getSchoolName(student.getSchoolId()))
                            .createdAt(student.getCreatedAt().toLocalDate())
                            .build())
                    .collect(Collectors.toList());

            return UserListResponseDto.builder()
                    .users(users)
                    .totalPages(studentPage.getTotalPages())
                    .totalElements(studentPage.getTotalElements())
                    .currentPage(studentPage.getNumber())
                    .build();
        } else {
            TeacherStatus teacherStatus = status != null ? TeacherStatus.valueOf(status) : null;
            Page<Teacher> teacherPage = teacherRepository.findTeachersWithFilters(schoolId, teacherStatus, name, pageable);

            List<UserListResponseDto.UserInfo> users = teacherPage.getContent().stream()
                    .map(teacher -> UserListResponseDto.UserInfo.builder()
                            .userId(teacher.getTeacherId())
                            .name(teacher.getTeacherName())
                            .username(teacher.getTeacherUsername())
                            .email(teacher.getTeacherEmail())
                            .role("TEACHER")
                            .status(teacher.getTeacherStatus().name())
                            .schoolName(getSchoolName(teacher.getSchoolId()))
                            .createdAt(teacher.getCreatedAt().toLocalDate())
                            .build())
                    .collect(Collectors.toList());

            return UserListResponseDto.builder()
                    .users(users)
                    .totalPages(teacherPage.getTotalPages())
                    .totalElements(teacherPage.getTotalElements())
                    .currentPage(teacherPage.getNumber())
                    .build();
        }
    }

    @Override
    public void approveUser(Long userId) {
        studentRepository.findById(userId).ifPresentOrElse(
                student -> {
                    student.setStudentStatus(StudentStatus.APPROVED);
                    studentRepository.save(student);

                    emailProvider.sendApprovalNotification(
                            student.getStudentEmail(),
                            student.getStudentName(),
                            "STUDENT"
                    );
                },
                () -> {
                    Teacher teacher = teacherRepository.findById(userId)
                            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));

                    teacher.setTeacherStatus(TeacherStatus.APPROVED);
                    teacherRepository.save(teacher);

                    emailProvider.sendApprovalNotification(
                            teacher.getTeacherEmail(),
                            teacher.getTeacherName(),
                            "TEACHER"
                    );
                }
        );
    }

    @Override
    public void rejectUser(Long userId) {
        studentRepository.findById(userId).ifPresentOrElse(
                student -> {
                    student.setStudentStatus(StudentStatus.REJECTED);
                    studentRepository.save(Student);
                },
                () -> {
                    Teacher teacher = teacherRepository.findById(userId)
                            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));

                    teacher.setTeacherStatus(TeacherStatus.RETIRED);
                    teacherRepository.save(teacher);
                }
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailResponseDto getUserDetail(Long userId) {
        return studentRepository.findById(userID)
                .map(student -> UserDetailResponseDto.builder()
                        .userId(student.getStudentId())
                        .name(student.getStudentName())
                        .username(student.getStudentUsername())
                        .email(student.getStudentEmail())
                        .phoneNumber(student.getStudentPhoneNumber())
                        .birthDate(student.getStudentBirthDate())
                        .role("STUDENT")
                        .status(student.getStudentStatus().name())
                        .schoolName(getSchoolName(student.getSchoolId()))
                        .grade(student.getStudentGrade())
                        .affiliation(student.getStudentAffiliation().name())
                        .admissionYear(student.getStudentAdmissionYear())
                        .build())
                .orElseGet(() -> {
                    Teacher teacher = teacherRepository.findById(userId)
                            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));

                    return UserDetailResponseDto.builder()
                            .userId(teacher.getTeacherId())
                            .name(teacher.getTeacherName())
                            .username(teacher.getTeacherUsername())
                            .email(teacher.getTeacherEmail())
                            .phoneNumber(teacher.getTeacherPhoneNumber())
                            .birthDate(teacher.getTeacherBirthDate())
                            .role("TEACHER")
                            .status(teacher.getTeacherStatus().name())
                            .schoolName(getSchoolName(teacher.getSchoolId()))
                            .subject(teacher.getTeacherSubject())
                            .build();
                });
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectListResponseDto getSubjects(String status, String subjectName, String teacherName, Pageable pageable) {
        Long schoolId = getCurrentSchoolId();
        SubjectStatus subjectStatus =status != null ? SubjectStatus.valueOf(status) : null;

        Page<Subject> subjectPage = subjectRepository.findSubjectWithFilters(schoolId, subjectStatus, subjectName, teacherName, pageable);

        List<SubjectListResponseDto.subjectInfo> subjects = subjectPage.getContent().stream()
                .map(subject -> {
                    SubjectMaster subjectMaster = subjectMasterRepository.findById(subject.getSubjectMasterId())
                            .orElseThrow(() -> new DataNotFoundException("과목 마스터 정보를 찾을 수 없습니다."));
                    Teacher teacher = teacherRepository.findById(subject.getTeacherId())
                            .orElseThrow(() -> new DataNotFoundException("교사 정보를 찾을 수 없습니다."));

                    return SubjectListResponseDto.SubjectInfo.builder()
                            .subjectId(subject.getSubjectId())
                            .subjectCode(subjectMaster.getSubjectCode())
                            .subjectName(subjectMaster.getSubjectName())
                            .teacherName(teacher.getTeacherName())
                            .targetGrade(subject.getSubjectTargetGrade())
                            .semester(subject.getSubjectStatus().getDescription())
                            .status(subject.getSubjectStatus().getDescription())
                            .maxEnrollment(subject.getSubjectMaxEnrollment())
                            .createdAt(subject.getCreatedAt().toLocalDate())
                            .build();
                })
                .collect(Collectors.toList());

        return SubjectListResponseDto.builder()
                .subjects(subjects)
                .totalPages(subjectPage.getTotalPages())
                .totalElements(subjectPage.getTotalElements())
                .currentPage(subjectPage.getNumber())
                .build();
    }

    @Override
    public void approveSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new DataNotFoundException("과목을 찾을 수 없습니다."));

        subject.setSubjectStatus(SubjectStatus.APPROVED);
        subjectRepository.save(subject);

        createLectureFromSubject(subject);
    }

    @Override
    public void rejectSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new DataNotFoundException("과목을 찾을 수 없습니다."));

        subject.setSubjectStatus(SubjectStatus.REJECTED);
        subjectRepository.save(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public LectureListResponseDto getLectures(String grade, String subjectName, String teacherName, Pageable pageable) {
        Long schoolId = getCurrentSchoolId();

        Page<Lecture> lecturePage = lectureRepository.findLecturesWithFilters(schoolId, grade, subjectName, teacherName, pageable);

        List<LectureListResponseDto.LectureInfo> lectures = lecturePage.getContent().stream()
                .map(lecture -> {
                    Subject subject = subjectRepository.findById(lecture.getSubjectId())
                            .orElseThrow(() -> new DataNotFoundException("과목 정보를 찾을 수 없습니다."));
                    SubjectMaster subjectMaster = subjectMasterRepository.findById(subject.getSubjectMasterId())
                            .orElseThrow(() -> new DataNotFoundException("과목 마스터 정보를 찾을 수 없습니다."));
                    Teacher teacher = teacherRepository.findById(lecture.getTeacherId())
                            .orElseThrow(() -> new DataNotFoundException("교사 정보를 찾을 수 없습니다."));

                    return LectureListResponseDto.LectureInfo.builder()
                            .lectureId(lecture.getLectureId())
                            .lectureCode(lecture.getLectureCode())
                            .lectureName(lecture.getLectureName())
                            .subjectName(subjectMaster.getSubjectName())
                            .teacherName(teacher.getTeacherName())
                            .allowedGrade(lecture.getLectureAllowedGrade())
                            .academicYear(lecture.getLectureAcademicYear())
                            .semester(lecture.getLectureSemester().getDescription())
                            .maxEnrollment(lecture.getLectureMaxEnrollment())
                            .currentEnrollment(lecture.getLectureCurrentEnrollment())
                            .build();
                })
                .collect(Collectors.toList());

        return LectureListResponseDto.builder()
                .lectures(lectures)
                .totalPages(lecturePage.getTotalPages())
                .totalElements(lecturePage.getTotalElements())
                .currentPage(lecturePage.getNumber())
                .build();
    }

    @Override
    public void createLecture(CreateLectureRequestDto request) {
        Long schoolId = getCurrentSchoolId();

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new DataNotFoundException("과목을 찾을 수 없습니다."));

        if (!subject.getSchoolId().equals(schoolId)) {
            throw new AuthorizationExcption("권한이 없습니다.");
        }

        Lecture lecture = new Lecture();
        lecture.setSchoolId(schoolId);
        lecture.setSubjectId(request.getSubjectId());
        lecture.setTeacherId(subject.getTeacherId());
        lecture.setLectureName(request.getLectureName());
        lecture.setLectureCode(request.getLectureCode());
        lecture.setLectureAcademicYear(request.getAcademicYear());
        lecture.setLectureSemester(LectureSemester.valueOf(request.getSemester()));
        lecture.setLectureAllowedGrade(request.getAllowedGrade());
        lecture.setLectureMaxEnrollment(request.getMaxEnrollment());

        lectureRepository.save(lecture);
    }

    @Override
    @Transactional(readOnly = true)
    public LectureDetailResponseDto getLectureDetail(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new DataNotFoundException("강의를 찾을 수 없습니다."));
        Subject subject = subjectRepository.findById(lecture.getSubjectId())
                .orElseThrow(() -> new DataNotFoundException("과목 정보를 찾을 수 없습니다."));
        SubjectMaster subjectMaster = subjectMasterRepository.findById(subject.getSubjectMasterId())
                .orElseThrow(() -> new DataNotFoundException("과목 마스터 정보를 찾을 수 없습니다."));
        Teacher teacher = teacherRepository.findById(lecture.getTeacherId())
                .orElseThrow(() -> new DataNotFoundException("교사 정보를 찾을 수 없습니다."));

        List<CourseRegistration> registrations = courseRegistrationRepository.findByLectureId(lectureId);

        List<LectureDetailResponseDto.StudentInfo> enrolledStudents = registrations.stream()
                .map(registration -> {
                    Student student = studentRepository.findById(registration.getStudentId())
                            .orElseThrow(() -> new DataNotFoundException("학생 정보를 찾을 수 없습니다."));

                    return LectureDetailResponseDto.StudentInfo.builder()
                            .studentId(student.getStudentId())
                            .studentName(student.getStudentName())
                            .studentNumber(student.getStudentNumber())
                            .grade(student.getStudentGrade())
                            .registrationStatus(registration.getCourseRegistrationStatus().getDescription())
                            .build();
                })
                .collect(Collectors.toList());

        return LectureDetailResponseDto.builder()
                .lectureId(lecture.getLectureId())
                .lectureCode(lecture.getLectureCode())
                .lectureName(lecture.getLectureName())
                .subjectName(subjectMaster.getSubjectName())
                .teacherName(teacher.getTeacherName())
                .allowedGrade(lecture.getLectureAllowedGrade())
                .semester(lecture.getLectureSemester().getDescription())
                .maxEnrollment(lecture.getLectureMaxEnrollment())
                .currentEnrollment(lecture.getLectureCurrentEnrollment())
                .enrolledStudents(enrolledStudents)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public NoticeListResponseDto getNotices(String title, Pageable pageable) {
        Long schoolId = getCurrentSchoolId();

        Page<Notice> noticePage = noticeRepository.findNoticesWithFilters(schoolId, title, null, pageable);

        List<NoticeListResponseDto.NoticeInfo> notices = noticePage.getContent().stream()
                .map(notice -> NoticeListResponseDto.NoticeInfo.builder()
                        .noticeId(notice.getNoticeId())
                        .title(notice.getNoticeTitle())
                        .authorName(notice.getNoticeAuthorName())
                        .targetAudience(notice.getNoticeTargetAudience().getDescription())
                        .startDate(notice.getNoticeStartDate())
                        .endDate(notice.getNoticeEndDate())
                        .viewCount(notice.getNoticeViewCount())
                        .createdAt(notice.getCreatedAt().toLocalDate())
                        .build())
                .collect(Collectors.toList());

        return NoticeListResponseDto.builder()
                .notices(notices)
                .totalPages(noticePage.getTotalPages())
                .totalElements(noticePage.getTotalElements())
                .currentPage(noticePage.getNumber())
                .build();
    }

    @Override
    public void createNotice(CreateNoticeRequestDto request) {
        Long schoolId = getCurrentSchoolId();
        String authorName = getCurrentAdminName();

        Notice notice = new Notice();
        notice.setSchoolId(schoolId);
        notice.setNoticeAuthorType(NoticeAuthorType.ADMIN);
        notice.setNoticeAuthorName(authorName);
        notice.setNoticeTitle(request.getTitle());
        notice.setNoticeContent(request.getContent());
        notice.setNoticeTargetAudience(NoticeTargetAudience.valueOf(request.getTargetAudience()));
        notice.setNoticeStartDate(request.getStartDate());
        notice.setNoticeEndDate(request.getEndDate());

        noticeRepository.save(notice);
    }

    @Override
    public void updateNotice(Long noticeId, UpdateNoticeRequestDto request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new DataNotFoundException("공지사항을 찾을 수 없습니다."));

        if (!notice.getSchoolId().equals(getCurrentSchoolId())) {
            throw new AuthorizationException("권한이 없습니다.");
        }

        notice.setNoticeTitle(request.getTitle());
        notice.setNoticeContent(request.getContent());
        notice.setNoticeTargetAudience(NoticeTargetAudience.valueOf(request.getTargetAudience()));
        notice.setNoticeStartDate(request.getStartDate());
        notice.setNoticeEndDate(request.getEndDate());

        noticeRepository.save(notice);
    }

    @Override
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new DataNotFoundException("공지사항을 찾을 수 없습니다."));

        if (!notice.getSchoolId().equals(getCurrentSchoolId())) {
            throw new AuthorizationException("권한이 없습니다.");
        }

        noticeRepository.delete(notice);
    }

    @Override
    @Transactional(readOnly = true)
    public InquiryListResponseDto getInquiries(String status, Pageable pageable) {
        Long schoolId = getCurrentSchoolId();
        InquiryStatus inquiryStatus = status != null ? InquiryStatus.valueOf(status) : null;

        Page<Inquiry> inquiryPage = inquiryRepository.findBySchoolIdAndStatus(schoolId,inquiryStatus, pageable);

        List<InquiryListResponseDto.InquiryInfo> inquiries = inquiryPage.getContent().stream()
                .map(inquiry -> InquiryListResponseDto.InquiryInfo.builder()
                        .inquiryId(inquiry.getInquiryId())
                        .title(inquiry.getInquiryTitle())
                        .content(inquiry.getInquiryContent())
                        .authorType(inquiry.getInquiryAuthorType().getDescription())
                        .status(inquiry.getInquiryStatus().getDescription())
                        .createdAt(inquiry.getCreatedAt().toLocalDate())
                        .build())
                .collect(Collectors.toList());

        return InquiryListResponseDto.builder()
                .inquiries(inquiries)
                .totalPages(inquiryPage.getTotalPages())
                .totalElements(inquiryPage.getTotalElements())
                .currentPage(inquiryPage.getNumber())
                .build();
    }

    @Override
    public void updateInquiryStatus(Long inquiryId, UpdateInquiryStatusRequestDto request) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new DataNotFoundException("문의사항을 찾을 수 없습니다."));

        if (!inquiry.getSchoolId().equals(getCurrentSchoolId())) {
            throw new AuthorizationException("권한이 없습니다.");
        }

        inquiry.setInquiryStatus(InquiryStatus.valueOf(request.getStatus()));
        inquiryRepository.save(inquiry);
    }

    @Override
    @Transactional
    public AdminDashboardStatsResponseDto getDashboardStats() {
        Long schoolId = getCurrentSchoolId();

        long totalStudents = studentRepository.countBySchoolId(schoolId);
        long totalTeachers = teacherRepository.countBySchoolId(schoolId);
        long totalSubjects = subjectRepository.countBySchoolId(schoolId);
        long totalLectures = lectureRepository.countBySchoolId(schoolId);
        long pendingApprovals = studentRepository.countBySchoolIdAndStudentStatus(schoolId, StudentStatus.PENDING) +
                teacherRepository.countBySchoolIdAndTeacherStatus(schoolId, TeacherStatus.PENDING) +
                subjectRepository.countBySchoolIdAndSubjectStatus(schoolId, SubjectStatus.PENDING);
        long totalNotices = noticeRepository.countBySchoolIdAndCreatedAtAfter(schoolId, LocalDateTime.now().minusDays(30));
        long newInquiries = inquiryRepository.countBySchoolIdAndInquiryStatus(schoolId, InquiryStatus.NEW);

        return AdminDashboardStatsResponseDto.builder()
                .totalStudents(totalStudents)
                .totalTeachers(totalTeachers)
                .totalSubjects(totalSubjects)
                .totalLectures(totalLectures)
                .pendingApprovals(pendingApprovals)
                .totalNotices(totalNotices)
                .newInquiries(newInquiries)
                .build();
    }

    private String getSchoolName(Long schoolId) {
        return schoolRepository.findById(schoolId)
                .map(School::getSchoolName)
                .orElse("Unknown School");
    }

    private String getCurrentAdminName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        School school =schoolRepository.findBySchoolAdminUsername(username)
                .orElseThrow(() -> new DataNotFoundException("학교 정보를 찾을 수 없습니다."));

        return adminRepository.findSchoolId(school.getSchoolId())
                .map(Admin::getAdminName)
                .orElse("관리자");
    }

    private void createLectureFromSubject(Subject subject) {
        SubjectMaster subjectMaster = subjectMasterRepository.findById(subject.getSubjectMasterId())
                .orElseThrow(() -> new DataNotFoundException("과목 마스터 정볼르 찾을 수 없습니다."));

        Lecture lecture = new Lecture();
        lecture.setSchoolId(subject.getSchoolId());
        lecture.setSubjectId(subject.getSubjectId());
        lecture.setTeacherId(subject.getTeacherId());
        lecture.setLectureName(subjectMaster.getSubjectName());
        lecture.setLectureCode(subjectMaster.getSubjectCode() + "_" + System.currentTimeMillis());
        lecture.setLectureAcademicYear(LocalDate.now().getYear());
        lecture.setLectureSemester(LectureSemester.valueOf(subject.getSubjectSemester().name()));
        lecture.setLectureAllowedGrade(subject.getSubjectTargetGrade());
        lecture.setLectureMaxEnrollment(subject.getSubjectMaxEnrollment());

        lectureRepository.save(lecture);
    }
}

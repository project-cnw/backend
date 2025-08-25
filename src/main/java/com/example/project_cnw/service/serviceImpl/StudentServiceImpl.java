package com.example.project_cnw.service.serviceImpl;

import com.example.project_cnw.common.enums.CourseRegistrationStatus;
import com.example.project_cnw.dto.request.student.UpdateProfileRequestDto;
import com.example.project_cnw.dto.response.student.CourseHistoryResponseDto;
import com.example.project_cnw.dto.response.student.StudentDashboardResponseDto;
import com.example.project_cnw.dto.response.student.StudentProfileResponseDto;
import com.example.project_cnw.entity.*;
import com.example.project_cnw.exception.DataNotFoundException;
import com.example.project_cnw.repository.*;
import com.example.project_cnw.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    






}

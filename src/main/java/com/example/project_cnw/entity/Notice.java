package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.NoticeAuthorType;
import com.example.project_cnw.common.enums.NoticeTargetAudience;
import com.example.project_cnw.entity.datetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_author_type", nullable = false)
    private NoticeAuthorType noticeAuthorType;

    @Column(name = "notice_author_name", nullable = false)
    private String noticeAuthorName = "교무처";

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_target_audience", nullable = false)
    private NoticeTargetAudience noticeTargetAudience;

    @Column(name = "notice_start_date", nullable = false)
    private LocalDate noticeStartDate;

    @Column(name = "notice_end_date", nullable = false)
    private LocalDate noticeEndDate;

    @Column(name = "notice_view_count")
    private Integer noticeViewCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

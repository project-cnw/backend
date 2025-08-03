package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.InquiryAuthorType;
import com.example.project_cnw.common.enums.InquiryStatus;
import com.example.project_cnw.entity.datetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long inquiryId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "inquiry_title", nullable = false)
    private String inquiryTitle;

    @Column(name = "inquiry_content", nullable = false, columnDefinition = "TEXT")
    private String inquiryContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "inquiry_author_type", nullable = false)
    private InquiryAuthorType inquiryAuthorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "inquiry_status")
    private InquiryStatus inquiryStatus = InquiryStatus.NEW;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

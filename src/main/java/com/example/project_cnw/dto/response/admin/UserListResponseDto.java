package com.example.project_cnw.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponseDto {
    private List<UserInfo> users;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private Long userId;
        private String name;
        private String username;
        private String email;
        private String role;
        private String status;
        private String schoolName;
        private LocalDate createdAt;
    }
}

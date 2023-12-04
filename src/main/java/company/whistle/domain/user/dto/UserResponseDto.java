package company.whistle.domain.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long userId;
    private String loginId;
    private String email;
    private String name;
    private String password;
    private String phone;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "userId=" + userId +
                ", loginId='" + loginId + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}

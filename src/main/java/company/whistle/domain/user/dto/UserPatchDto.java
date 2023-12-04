package company.whistle.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPatchDto {
    private Long userId;
    private String loginId;

    @Email(message = "올바른 형식으로 입력해야 합니다.")
    @Length(min = 1, max = 60, message = "사이즈를 확인하세요.")
    private String email;

    @Size(min = 2, max = 16, message = "닉네임 길이는 2글자 이상 6글자 이하여야 합니다")
//    @Pattern(regexp = "\t /^[가-힣a-zA-Z]+$/", message = "비밀번호는 영문, 특수문자, 숫자 포함 8-16자 이내여야 합니다.")
    private String name;

//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&]).{8,16}", message = "비밀번호는 영문, 특수문자, 숫자 포함 8-16자 이내여야 합니다.")
    private String password;

//    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "ds")
    private String phone;

    private String position;
}

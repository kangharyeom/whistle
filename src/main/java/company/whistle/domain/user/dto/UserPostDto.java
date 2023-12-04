package company.whistle.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDto {
    @NotBlank(message = "아이디는 공백이 아니어야 합니다.")
    private String loginId;

    @Email(message = "올바른 형식으로 입력해야 합니다.")
    @Length(min = 1, max = 60, message = "사이즈를 확인하세요.")
    @NotBlank(message = "이메일은 공백이 아니어야 합니다.")
    private String email;

    @Size(min = 2, max = 16, message = "이름 길이는 2글자 이상 6글자 이하여야 합니다")
    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
//    @Pattern(regexp = "\t /^[가-힣a-zA-Z]+$/", message = "이름은 한글 또는 영문이어야 합니다.")
    private String name;

    @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&]).{8,16}", message = "비밀번호는 영문, 특수문자, 숫자 포함 8-16자 이내여야 합니다.")
    private String password;

//    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "ds")
    @NotBlank(message = "전화번호는 공백이 아니어야 합니다.")
    private String phone;
}

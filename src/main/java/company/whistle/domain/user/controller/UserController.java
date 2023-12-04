package company.whistle.domain.user.controller;

import company.whistle.domain.user.dto.UserPatchDto;
import company.whistle.domain.user.dto.UserPostDto;
import company.whistle.domain.user.dto.UserResponseDto;
import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.mapper.UserMapper;
import company.whistle.domain.user.repository.UserRepository;
import company.whistle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Validated
@Log4j2
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    /*
    * 회원 가입
    */
    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> postUser(@RequestBody @Validated UserPostDto requestBody) {

        User user = userService.createUser(userMapper.userPostDtoToUser(requestBody));
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);
        log.info("USER POST FINISHED: {}", userResponseDto);

        return ResponseEntity.ok(userResponseDto);
    }

    // 회원 정보 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> patchUser(@PathVariable("userId") @Positive Long userId,
                                      @RequestBody @Valid UserPatchDto requestBody) {
        requestBody.setUserId(userId);

        User user = userService.updateUser(userMapper.userPatchDtoToUser(requestBody), userId);
        user.setUserId(userId);

        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);
        log.info("USER PATCH FINISHED: {}", userResponseDto.toString());

        return ResponseEntity.ok(userResponseDto);
    }

    // 회원 단건 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("userId") @Positive Long userId) {
        User user = userService.findUser(userId);
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);

        return ResponseEntity.ok(userResponseDto);
    }

    // 회원 전체 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser() {
        List<User> users = userService.findAllUser();
        List<UserResponseDto> userResponseDtos = userMapper.usersToUsersResponse(users);

        return ResponseEntity.ok(userResponseDtos);
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") @Positive Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
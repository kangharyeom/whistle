package company.board_project.user.service;

import company.board_project.exception.BusinessLogicException;
import company.board_project.exception.Exceptions;
import company.board_project.user.entity.User;
import company.board_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원 가입
    public User createUser(User user) {
        // 이메일 유무 확인
        verifiedUser(user.getEmail());

        // repository에 회원 등록
        userRepository.save(user);

        return user;
    }

    // 회원 수정
    public User updateUser(User user) {
        // 회원 유무 확인
        User findUser = existUser(user.getUserId());

        // 새로 변경할 이메일 존재 유무 확인
        Optional.ofNullable(user.getEmail())
                .ifPresent(newEmail -> {
                    verifiedUser(findUser.getEmail());
                    findUser.setEmail(newEmail);
                });

        Optional.ofNullable(user.getPassword())
                .ifPresent(findUser::setPassword);

        Optional.ofNullable(user.getName())
                .ifPresent(findUser::setName);

        return userRepository.save(findUser);
    }

    // 회원 단건 조회
    public User findUser(Long userId) {
        return existUser(userId);
    }

    // 회원 전체 조회
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    // 회원 탈퇴
    public void deleteUser(Long userId) {
        User findUser = existUser(userId);
        userRepository.delete(findUser);
    }

    // 회원 존재 유무 확인 메서드
    public User existUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(Exceptions.USER_NOT_FOUND));
    }

    // 이메일 중복 검사
    public void verifiedUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            throw new BusinessLogicException(Exceptions.EMAIL_EXISTS);
        }
    }

    /*// 로그인한 회원
    public User getLoginUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(Exceptions.USER_NOT_FOUND));
    }*/
}
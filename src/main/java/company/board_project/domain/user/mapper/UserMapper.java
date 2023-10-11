package company.board_project.domain.user.mapper;

import company.board_project.domain.user.entity.User;
import company.board_project.global.constant.Position;
import company.board_project.domain.user.dto.UserListDto;
import company.board_project.domain.user.dto.UserPatchDto;
import company.board_project.domain.user.dto.UserPostDto;
import company.board_project.domain.user.dto.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default User userPostDtoToUser(UserPostDto requestBody){
        User user = new User();

        user.setLoginId(requestBody.getLoginId());
        user.setEmail( requestBody.getEmail() );
        user.setName( requestBody.getName() );
        user.setPassword( requestBody.getPassword() );
        user.setPhone( requestBody.getPhone() );
        user.setPosition(Position.valueOf(requestBody.getPosition()));

        return user;
    }

    default User userPatchDtoToUser(UserPatchDto requestBody){
        User user = new User();

        user.setUserId( requestBody.getUserId() );
        user.setEmail( requestBody.getEmail() );
        user.setLoginId( requestBody.getLoginId() );
        user.setName( requestBody.getName() );
        user.setPassword( requestBody.getPassword() );
        user.setPhone( requestBody.getPhone() );
        user.setPosition(Position.valueOf(requestBody.getPosition()));

        return user;
    }

    // 회원 단건 조회
    default UserResponseDto userToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .phone(user.getPhone())
                .position(String.valueOf(user.getPosition()))
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }

    // 회원 전체 List
    default UserListDto usersToUserListResponse(List<User> userList) {
        return UserListDto.builder()
                .userResponseDto(usersToUsersResponse(userList))
                .build();
    }

    // 회원 response 리스트화
    default List<UserResponseDto> usersToUsersResponse(List<User> users){
        return users.stream()
                .map(user -> UserResponseDto.builder()
                        .userId(user.getUserId())
                        .loginId(user.getLoginId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .password(user.getPassword())
                        .phone(user.getPhone())
                        .position(String.valueOf(user.getPosition()))
                        .createdAt(user.getCreatedAt())
                        .modifiedAt(user.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
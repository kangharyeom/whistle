package company.whistle.domain.user.mapper;

import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.dto.UserListDto;
import company.whistle.domain.user.dto.UserPatchDto;
import company.whistle.domain.user.dto.UserPostDto;
import company.whistle.domain.user.dto.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userPostDtoToUser(UserPostDto requestBody);

    User userPatchDtoToUser(UserPatchDto requestBody);

    // 회원 단건 조회
    UserResponseDto userToUserResponseDto(User user);

    // 회원 전체 List
    default UserListDto usersToUserListResponse(List<User> userList) {
        return UserListDto.builder()
                .userResponseDto(usersToUsersResponse(userList))
                .build();
    }

    // 회원 response 리스트화
    List<UserResponseDto> usersToUsersResponse(List<User> users);
}

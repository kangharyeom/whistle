package company.whistle.domain.team.squad.mapper;

import company.whistle.domain.team.squad.dto.SquadResponseDto;
import company.whistle.domain.team.squad.dto.SquadPatchDto;
import company.whistle.domain.team.squad.dto.SquadPostDto;
import company.whistle.domain.team.squad.dto.SquadResponseListDto;
import company.whistle.domain.team.squad.entity.Squad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SquadMapper {
    Squad squadPostDtoToSquad(SquadPostDto requestBody);

    Squad squadPatchDtoToSquad(SquadPatchDto requestBody);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "team.teamId", target = "teamId")
    SquadResponseDto squadToSquadResponse(Squad squad);

    default SquadResponseListDto squadDtoToSquadResponse(List<Squad> squads){
        return SquadResponseListDto.builder()
                .squadResponseDtoList(squadsToSquadResponse(squads))
                .build();
    }

    List<SquadResponseDto> squadsToSquadResponse(List<Squad> squads);
}
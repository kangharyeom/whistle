package company.whistle.domain.league.participants.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantsResponseListDto {
    private List<ParticipantsResponseDto> participants;
}

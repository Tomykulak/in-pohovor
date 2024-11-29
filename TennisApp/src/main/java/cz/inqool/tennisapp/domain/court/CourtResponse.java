package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.tennisClub.TennisClub;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse{
    private int id;
    private String name;
    private int tennisClubId;

    public CourtResponse(Court court) {
        this.id = court.getId();
        this.name = court.getName();
        if (court.getTennisClub() != null) {
            this.tennisClubId = court.getTennisClub().getId();
        } else {
            this.tennisClubId = 0;
        }
    }
}

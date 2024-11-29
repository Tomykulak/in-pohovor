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

    private String surfaceType;
    private int surfaceCost;

    public CourtResponse(Court court) {
        this.id = court.getId();
        this.name = court.getName();
        if (court.getTennisClub() != null) {
            this.tennisClubId = court.getTennisClub().getId();
        } else {
            this.tennisClubId = 0;
        }
        if (court.getSurfaceType() != null) {
            this.surfaceType = court.getSurfaceType().getName();
            this.surfaceCost = court.getSurfaceType().getCost();
        } else {
            this.surfaceType = "UNKNOWN";
            this.surfaceCost = 0;
        }
    }
}

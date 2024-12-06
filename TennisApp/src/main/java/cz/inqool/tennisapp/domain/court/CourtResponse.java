package cz.inqool.tennisapp.domain.court;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse{
    private int id;
    private String name;
    private boolean isDeleted;
    private int tennisClubId;

    private String surfaceType;
    private double surfaceCost;

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
            this.surfaceCost = court.getSurfaceType().getPricePerMinute();
        } else {
            this.surfaceType = "UNKNOWN";
            this.surfaceCost = 0;
        }
    }
}

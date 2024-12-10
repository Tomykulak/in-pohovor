package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.utils.exceptions.BadRequestException;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse {
    private int id;

    private String name;

    private boolean isDeleted;

    private String surfaceType;

    private double surfaceCost;

    public CourtResponse(Court court) {
        if (court == null) {
            throw new NotFoundException();
        }

        this.id = court.getId();
        this.name = court.getName();
        this.isDeleted = court.isDeleted();

        if (court.getSurfaceType() != null) {
            this.surfaceType = court.getSurfaceType().getName();
            this.surfaceCost = court.getSurfaceType().getPricePerMinute();
        } else {
            this.surfaceType = "UNKNOWN";
            this.surfaceCost = 0;
        }
    }
}

package cz.inqool.tennisapp.domain.tennisClub;

import cz.inqool.tennisapp.domain.court.Court;
import lombok.Data;

import java.util.Set;

@Data
public class TennisClubResponse {
    private int id;
    private String name;
    private Set<Court> courts;

    public TennisClubResponse(TennisClub tennisClub) {
        this.id = tennisClub.getId();
        this.name = tennisClub.getName();
        this.courts = tennisClub.getCourts();
    }
}

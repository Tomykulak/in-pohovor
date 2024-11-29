package cz.inqool.tennisapp.domain.tennisClub;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtResponse;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class TennisClubResponse {
    private int id;
    private String name;
    private Set<CourtResponse> courts;

    public TennisClubResponse(TennisClub tennisClub) {
        this.id = tennisClub.getId();
        this.name = tennisClub.getName();
        this.courts = tennisClub.getCourts().stream()
                .map(CourtResponse::new)
                .collect(Collectors.toSet());
    }
}

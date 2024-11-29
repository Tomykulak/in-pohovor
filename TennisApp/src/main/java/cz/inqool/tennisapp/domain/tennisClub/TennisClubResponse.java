package cz.inqool.tennisapp.domain.tennisClub;

import lombok.Data;

@Data
public class TennisClubResponse {
    private int id;
    private String name;

    public TennisClubResponse(TennisClub tennisClub) {
        this.name = name;
    }
}

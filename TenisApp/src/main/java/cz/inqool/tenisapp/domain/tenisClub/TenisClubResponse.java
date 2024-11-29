package cz.inqool.tenisapp.domain.tenisClub;

import lombok.Data;

@Data
public class TenisClubResponse {
    private int id;
    private String name;

    public TenisClubResponse(TenisClub tenisClub) {
        this.id = id;
        this.name = name;
    }
}

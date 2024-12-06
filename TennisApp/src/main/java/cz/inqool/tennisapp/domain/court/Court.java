package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.domain.tennisClub.TennisClub;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    // attribute for softDelete
    private boolean deleted = false;

    // linked to surface
    @ManyToOne
    @JoinColumn(name = "surface_type_id")
    private SurfaceType surfaceType;

    // linked to tennisClub
    @ManyToOne
    @JoinColumn(name = "tennis_club_id")
    private TennisClub tennisClub;

    public Court(String name, SurfaceType surfaceType, TennisClub tennisClub) {
        this.name = name;
        this.surfaceType = surfaceType;
        this.tennisClub = tennisClub;
    }
}

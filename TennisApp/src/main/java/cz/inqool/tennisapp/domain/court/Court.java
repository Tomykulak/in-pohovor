package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.domain.tennisClub.TennisClub;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "surface_type_id", nullable = false) // Link to Surface
    private SurfaceType surfaceType;

    @ManyToOne
    @JoinColumn(name = "tennis_club_id", nullable = false)
    private TennisClub tennisClub;

}

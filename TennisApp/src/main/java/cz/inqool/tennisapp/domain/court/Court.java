package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.reservation.Reservation;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.domain.tennisClub.TennisClub;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    // attribute for softDelete
    private boolean isDeleted;

    // linked to surface
    @ManyToOne
    @JoinColumn(name = "surface_type_id", nullable = false)
    private SurfaceType surfaceType;

    // linked to tennisClub
    @ManyToOne
    @JoinColumn(name = "tennis_club_id", nullable = false)
    private TennisClub tennisClub;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}

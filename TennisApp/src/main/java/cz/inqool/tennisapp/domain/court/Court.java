package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
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


    public Court(String name, SurfaceType surfaceType) {
        this.name = name;
        this.surfaceType = surfaceType;
    }
}

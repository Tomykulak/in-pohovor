package cz.inqool.tenisapp.domain.court;

import cz.inqool.tenisapp.domain.tenisClub.TenisClub;
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
    @JoinColumn(name = "tenis_club_id")
    private TenisClub tenisClub;
}

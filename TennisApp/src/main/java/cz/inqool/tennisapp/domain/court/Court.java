package cz.inqool.tennisapp.domain.court;

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
    @JoinColumn(name = "tennis_club_id", nullable = false)
    private TennisClub tennisClub;

}

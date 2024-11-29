package cz.inqool.tennisapp.domain.tennisClub;

import cz.inqool.tennisapp.domain.court.Court;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TennisClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    /*
    // one TennisClub has multiple Courts
    @OneToMany(mappedBy = "tennis_club")
    @EqualsAndHashCode.Exclude
    private Set<Court> courts = new HashSet<Court>();
    */

}

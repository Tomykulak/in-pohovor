package cz.inqool.tenisapp.domain.tenisClub;

import cz.inqool.tenisapp.domain.court.Court;
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
public class TenisClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    // one TenisClub has multiple Courts
    @OneToMany(mappedBy = "tenis_club")
    @EqualsAndHashCode.Exclude
    private Set<Court> courts = new HashSet<Court>();


}

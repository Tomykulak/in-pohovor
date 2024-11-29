package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String customerName;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;
}

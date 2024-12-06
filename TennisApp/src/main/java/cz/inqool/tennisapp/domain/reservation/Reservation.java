package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private int id;

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "surfaceType_id")
    private SurfaceType surfaceType;

    private boolean isDoubles;

    private Double price;

    private boolean isDeleted = false;

    public Reservation(Court court, Customer customer, LocalDateTime startTime, LocalDateTime endTime, boolean isDoubles, Double price) {
        this.court = court;
        this.customer = customer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDoubles = isDoubles;
        this.price = price;
    }
}

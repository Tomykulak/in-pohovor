package cz.inqool.tennisapp.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private int id;

    private String courtName;

    private String customerName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isDoubles;

    private boolean isDeleted;

    private Double price;


    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.courtName = reservation.getCourt().getName();
        this.customerName = reservation.getCustomer().getName();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
        this.isDoubles = reservation.isDoubles();
        this.isDeleted = reservation.isDeleted();
        this.price = reservation.getPrice();
    }
}

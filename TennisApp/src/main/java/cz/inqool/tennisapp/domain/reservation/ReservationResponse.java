package cz.inqool.tennisapp.domain.reservation;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ReservationResponse {
    private int id;
    private String courtName;
    private String customerName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.courtName = reservation.getCourt().getName();
        this.customerName = reservation.getCustomer().getName();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
    }
}

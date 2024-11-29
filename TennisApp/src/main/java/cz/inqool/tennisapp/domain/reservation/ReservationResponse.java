package cz.inqool.tennisapp.domain.reservation;

import java.time.LocalDateTime;

public class ReservationResponse {
    private Long id;
    private String courtName;
    private String customerName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ReservationResponse(Reservation reservation) {
        this.id = (long) reservation.getId();
        this.courtName = reservation.getCourt().getName();
        this.customerName = reservation.getCustomer().getName();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
    }
}

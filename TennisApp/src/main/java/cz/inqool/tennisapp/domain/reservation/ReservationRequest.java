package cz.inqool.tennisapp.domain.reservation;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationRequest {
    private int courtId;
    private String customerName;
    private String phoneNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

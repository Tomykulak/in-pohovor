package cz.inqool.tennisapp.domain.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    private int courtId;

    @NotBlank
    private String customerName;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private boolean isDoubles;
}

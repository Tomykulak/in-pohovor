package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ObjectResponse<ReservationResponse> getReservationById(@PathVariable int id) {
        Reservation reservation = reservationService
                .getReservationById(id);
        if (reservation == null) {
            throw new NotFoundException();
        }
        return ObjectResponse.of(reservation, ReservationResponse::new);
    }

    @GetMapping(value = "/phone-number/{phoneNumber}", produces = "application/json")
    public ArrayResponse<ReservationResponse> getReservationsForPhoneNumber(
            @PathVariable String phoneNumber,
            @RequestParam(required = false, defaultValue = "false") boolean futureOnly) {
        List<Reservation> reservations = reservationService.getReservationsForPhoneNumber(phoneNumber, futureOnly);
        return ArrayResponse.of(reservations, ReservationResponse::new);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ObjectResponse<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.createReservation(
                reservationRequest.getCourtId(),
                reservationRequest.getCustomerName(),
                reservationRequest.getPhoneNumber(),
                reservationRequest.getStartTime(),
                reservationRequest.getEndTime()
        );
        return ObjectResponse.of(reservation, ReservationResponse::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        Reservation reservation = reservationService
                .getReservationById(id);
        if (reservation == null) {
            throw new NotFoundException();
        }
        reservationService.deleteReservation(reservation.getId());
    }
}

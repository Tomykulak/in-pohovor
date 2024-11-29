package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.reservation.Reservation;
import cz.inqool.tennisapp.domain.reservation.ReservationService;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import cz.inqool.tennisapp.utils.response.ArrayResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/court")
public class CourtController {
    private final CourtService courtService;
    private final ReservationService reservationService;

    public CourtController(CourtService courtService, ReservationService reservationService) {
        this.courtService = courtService;
        this.reservationService = reservationService;
    }

    @GetMapping(value = "", produces = "application/json")
    public ArrayResponse<CourtResponse> getAllCourts(){
        List<Court> courts = courtService.getAllCourts();
        return ArrayResponse.of(courts,
                CourtResponse::new
        );
    }

    @GetMapping("/{courtId}/reservations")
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable int courtId) {
        List<Reservation> reservations = reservationService.getReservationsForCourt(courtId);
        return ResponseEntity.ok(reservations);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteCourt(@PathVariable int id) {
        Court court = courtService
                .getCourtById(id)
                .orElseThrow(NotFoundException::new);
        // only SOFT Delete
        courtService.deleteCourt(court);
    }
}

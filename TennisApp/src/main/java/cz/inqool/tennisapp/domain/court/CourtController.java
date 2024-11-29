package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.reservation.Reservation;
import cz.inqool.tennisapp.domain.reservation.ReservationResponse;
import cz.inqool.tennisapp.domain.reservation.ReservationService;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
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
        return ArrayResponse.of(
                courts,
                CourtResponse::new
        );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ObjectResponse<CourtResponse> getCourtById(@PathVariable int id) {
        Court court = courtService.getCourtById(id);
        if (court == null) {
            throw new NotFoundException();
        }
        return ObjectResponse.of(court, CourtResponse::new);
    }


    @GetMapping("/{courtId}/reservation")
    public ArrayResponse<ReservationResponse> getReservations(@PathVariable int courtId) {
        List<Reservation> reservations = reservationService.getReservationsForCourt(courtId);
        return ArrayResponse.of(
                reservations,
                ReservationResponse::new
        );
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteCourt(@PathVariable int id) {
        Court court = courtService
                .getCourtById(id);
        if (court == null) {
            throw new NotFoundException();
        }
        // only SOFT Delete
        courtService.deleteCourt(court);
    }
}

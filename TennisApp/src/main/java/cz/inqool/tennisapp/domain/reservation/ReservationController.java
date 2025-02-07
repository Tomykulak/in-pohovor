package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservation")
@Tag(name = "Reservations", description = "Manage reservations.")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping(value = "/court/{courtId}")
    @Operation(summary = "Retrieve all reservations of a court by a court id.", description = "Retrieve all reservations for a specific court by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully.",
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid court ID."),
        @ApiResponse(responseCode = "404", description = "Court not found.")
    })
    public ArrayResponse<ReservationResponse> getReservationsByCourtId(@PathVariable int courtId) {
        List<Reservation> reservations = reservationService.getReservationsByCourtId(courtId);
        return ArrayResponse.of(reservations, ReservationResponse::new);
    }


    @GetMapping("/customer/{phoneNumber}")
    @Operation(summary = "Retrieve all reservations by customer phone.", description = "Retrieve all reservations associated with a customer's phone number.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully.",
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid phone number."),
        @ApiResponse(responseCode = "404", description = "Customer not found.")
    })
    public ArrayResponse<ReservationResponse> getReservationsByCustomerPhoneNumber(@PathVariable String phoneNumber) {
        List<Reservation> reservations = reservationService.getReservationsByCustomerPhoneNumber(phoneNumber);
        return ArrayResponse.of(reservations, ReservationResponse::new);
    }


    @GetMapping("/customer/upcoming/{phoneNumber}")
    @Operation(summary = "Retrieve all upcoming reservations by customer phone number.", description = "Retrieve all upcoming reservations for a customer by phone number.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Upcoming reservations retrieved successfully.",
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid phone number."),
        @ApiResponse(responseCode = "404", description = "Customer not found or no upcoming reservations.")
    })
    public ArrayResponse<ReservationResponse> getUpcomingReservationsByCustomerPhoneNumber(@PathVariable String phoneNumber) {
        List<Reservation> reservations = reservationService.getUpcomingReservationsByCustomerPhoneNumber(phoneNumber);
        return ArrayResponse.of(reservations, ReservationResponse::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new reservation",
            description = "Add a new reservation for a tennis court.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reservation created successfully.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data."),
        @ApiResponse(responseCode = "404", description = "Court not found."),
        @ApiResponse(responseCode = "409", description = "Reservation conflict.")
    })
    public ObjectResponse<Double> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        double totalCost = reservationService.createReservation(
                reservationRequest.getCourtId(),
                reservationRequest.getCustomerName(),
                reservationRequest.getPhoneNumber(),
                reservationRequest.getStartTime(),
                reservationRequest.getEndTime(),
                reservationRequest.isDoubles()
        );

        return ObjectResponse.of(totalCost, Double::valueOf);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing reservation by its ID.",
        description = "Update the details of an existing reservation, including court, customer information, start time, and end time.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation updated successfully.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data."),
        @ApiResponse(responseCode = "404", description = "Reservation not found."),
        @ApiResponse(responseCode = "409", description = "Reservation conflict.")
    })
    public ObjectResponse<ReservationResponse> updateReservation(
            @PathVariable int id,
            @Valid @RequestBody ReservationRequest reservationRequest) {
        return ObjectResponse.of(reservationService.updateReservation(
                id,
                reservationRequest.getCourtId(),
                reservationRequest.getCustomerName(),
                reservationRequest.getPhoneNumber(),
                reservationRequest.getStartTime(),
                reservationRequest.getEndTime(),
                reservationRequest.isDoubles()
        ), ReservationResponse::new);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reservation by id.", description = "Soft delete a reservation by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Reservation deleted successfully."),
        @ApiResponse(responseCode = "404", description = "Reservation not found."),
        @ApiResponse(responseCode = "409", description = "Reservation cannot be deleted due to conflicts.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }


}

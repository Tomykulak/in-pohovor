package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.reservation.Reservation;
import cz.inqool.tennisapp.domain.reservation.ReservationResponse;
import cz.inqool.tennisapp.domain.reservation.ReservationService;
import cz.inqool.tennisapp.utils.exceptions.ActiveReservationsException;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/court")
@Tag(name = "Courts", description = "Manage tennis courts and their reservations.")
public class CourtController {
    private final CourtService courtService;

    @GetMapping("")
    @Operation(summary = "Retrieve all courts", description = "Fetch a list of all tennis courts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved courts.")
    })
    public ArrayResponse<CourtResponse> getAllCourts() {
        List<Court> courts = courtService.getAllCourts();
        return ArrayResponse.of(courts, CourtResponse::new);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve court by ID", description = "Fetch details of a specific tennis court by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved court."),
            @ApiResponse(responseCode = "404", description = "Court not found.")
    })
    public ObjectResponse<CourtResponse> getCourtById(@PathVariable int id) {
        Court court = courtService.getCourtById(id);
        return ObjectResponse.of(court, CourtResponse::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "Soft delete a court by ID", description = "Mark a tennis court as deleted by setting the 'isDeleted' flag to true.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Court deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Court not found."),
            @ApiResponse(responseCode = "409", description = "Court cannot be deleted because it has active reservations.")
    })
    public void deleteCourtById(@PathVariable int id) {
        courtService.deleteCourtById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a court by ID",
            description = "Update the details of a tennis court specified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Court updated successfully.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Court not found."),
            @ApiResponse(responseCode = "409", description = "Conflict occurred while updating the court.")
    })
    public ObjectResponse<CourtResponse> updateCourt(@PathVariable int id, @Valid @RequestBody Court courtRequest) {
        Court updatedCourt = courtService.updateCourt(id, courtRequest);
        return ObjectResponse.of(updatedCourt, CourtResponse::new);
    }

}

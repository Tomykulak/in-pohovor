package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.tennisClub.TennisClub;
import cz.inqool.tennisapp.domain.tennisClub.TennisClubResponse;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import cz.inqool.tennisapp.utils.response.ArrayResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/court")
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping(value = "", produces = "application/json")
    public ArrayResponse<CourtResponse> getAllCourts(){
        List<Court> courts = courtService.getAllCourts();
        return ArrayResponse.of(courts,
                CourtResponse::new
        );
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

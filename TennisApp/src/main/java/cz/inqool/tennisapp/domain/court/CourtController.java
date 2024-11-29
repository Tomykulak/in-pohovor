package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/court")
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }


    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteCourt(@PathVariable int id) {
        Court court = courtService
                .getCourtById(id)
                .orElseThrow(NotFoundException::new);
        courtService.deleteCourt(court);
    }
}

package cz.inqool.tennisapp.domain.court;

import org.springframework.web.bind.annotation.RestController;

@RestController("api/court")
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

}

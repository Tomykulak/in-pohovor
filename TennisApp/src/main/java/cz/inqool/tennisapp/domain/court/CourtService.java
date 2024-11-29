package cz.inqool.tennisapp.domain.court;

import org.springframework.stereotype.Service;

@Service
public class CourtService {
    private final CourtRepository courtRepository;

    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public Court createCourt(Court court) {
        return courtRepository.save(court);
    }
}

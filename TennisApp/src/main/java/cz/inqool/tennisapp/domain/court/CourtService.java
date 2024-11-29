package cz.inqool.tennisapp.domain.court;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourtService {
    private final CourtRepository courtRepository;

    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public Optional<Court> getCourtById(int id) {
        return courtRepository.findById((long) id);
    }

    public Court createCourt(Court court) {
        return courtRepository.save(court);
    }

    public Court updateCourt(Court court) {
        return courtRepository.save(court);
    }

    // only soft delete, so update isDeleted to true
    public void deleteCourt(Court court) {
        court.setDeleted(true);
        courtRepository.save(court);
    }
}

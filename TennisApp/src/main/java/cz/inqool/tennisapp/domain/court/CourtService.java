package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class CourtService {
    private final CourtRepository courtRepository;

    public List<Court> getAllCourts() {
        return courtRepository.findByDeletedFalse();
    }

    public Court getCourtById(int id) {
        return courtRepository.findById((long) id)
                .orElseThrow(NotFoundException::new);
    }

    public Court createCourt(int id, Court updateCourt) {
        Court court = courtRepository.findById((long) id)
                .orElseThrow(NotFoundException::new);

        if (court.isDeleted()) {
            throw new IllegalArgumentException("Cannot update a deleted court.");
        }
        if (updateCourt.getName() == null || updateCourt.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Court name required.");
        }
        if (updateCourt.getSurfaceType() == null) {
            throw new IllegalArgumentException("Surface type required.");
        }
        // set params
        court.setName(updateCourt.getName());
        court.setSurfaceType(updateCourt.getSurfaceType());
        // save
        return courtRepository.save(court);
    }

    public Court updateCourt(int id, Court updatedCourt) {
        Court court = courtRepository.findById((long) id)
                .orElseThrow(NotFoundException::new);

        if (court.isDeleted()) {
            throw new IllegalArgumentException("Cannot update deleted court.");
        }

        if (updatedCourt.getName() == null || updatedCourt.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Court name required");
        }
        if (updatedCourt.getSurfaceType() == null) {
            throw new IllegalArgumentException("Surface type required");
        }

        court.setName(updatedCourt.getName());
        court.setSurfaceType(updatedCourt.getSurfaceType());
        return courtRepository.save(court);
    }

    // only soft delete, so update isDeleted to true
    public void deleteCourtById(int id) {
        Court court = courtRepository.findById((long) id)
                .orElseThrow(NotFoundException::new);

        if (court.isDeleted()) {
            throw new IllegalArgumentException("Court already deleted.");
        }
        court.setDeleted(true);
        courtRepository.save(court);
    }
}

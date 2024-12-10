package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.utils.exceptions.AlreadyDeletedException;
import cz.inqool.tennisapp.utils.exceptions.BadRequestException;
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

    public Court createCourt(Court court) {
        if (court.getName() == null || court.getName().trim().isEmpty()) {
            throw new BadRequestException();
        }
        if (court.getSurfaceType() == null) {
            throw new BadRequestException();
        }
        return courtRepository.save(court);
    }

    public Court updateCourt(int id, Court updatedCourt) {
        Court court = courtRepository.findById((long) id)
                .orElseThrow(NotFoundException::new);

        if (court.isDeleted()) {
            throw new AlreadyDeletedException();
        }

        if (updatedCourt.getName() == null || updatedCourt.getName().trim().isEmpty()) {
            throw new BadRequestException();
        }
        if (updatedCourt.getSurfaceType() == null) {
            throw new BadRequestException();
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
            throw new AlreadyDeletedException();
        }
        court.setDeleted(true);
        courtRepository.save(court);
    }
}

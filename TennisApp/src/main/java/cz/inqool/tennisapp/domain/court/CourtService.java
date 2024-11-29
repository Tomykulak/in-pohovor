package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.reservation.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourtService {
    private final CourtRepository courtRepository;

    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public List<Court> getAllCourts() {
        List<Court> courts = new ArrayList<>();
        courtRepository.findAll().forEach(courts::add);
        return courts;
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
        if(!court.getReservations().isEmpty()){
            throw new RuntimeException("Cannot delete court with active reservations.");
        }
        court.setDeleted(true);
        courtRepository.save(court);
    }

    public List<Reservation> getReservations(Court court) {
        return court.getReservations();
    }
}

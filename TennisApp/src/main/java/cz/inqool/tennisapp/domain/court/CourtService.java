package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.reservation.Reservation;
import cz.inqool.tennisapp.utils.exceptions.ActiveReservationsException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Court getCourtById(int id) {
        return courtRepository.findById((long) id).orElse(null);
    }

    public Court createCourt(Court court) {
        return courtRepository.save(court);
    }

    public Court updateCourt(Court court) {
        return courtRepository.save(court);
    }

    // only soft delete, so update isDeleted to true
    public void deleteCourtById(Court court) {
        if(!court.getReservations().isEmpty()){
            throw new ActiveReservationsException("Cannot delete court with active reservations.");
        }
        court.setDeleted(true);
        courtRepository.save(court);
    }

    public List<Reservation> getReservations(Court court) {
        return court.getReservations();
    }
}

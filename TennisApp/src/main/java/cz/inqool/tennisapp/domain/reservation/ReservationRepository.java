package cz.inqool.tennisapp.domain.reservation;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByCourtIdAndDeletedFalse(int courtId);

    List<Reservation> findByCustomerPhoneNumberAndDeletedFalse(String customerPhoneNumber);

    List<Reservation> findByCustomerPhoneNumberAndEndTimeAfterAndDeletedFalse(String customerPhoneNumber, LocalDateTime currentTime);
}

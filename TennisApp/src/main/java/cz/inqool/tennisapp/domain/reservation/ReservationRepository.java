package cz.inqool.tennisapp.domain.reservation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByCourtIdAndDeletedFalse(Long courtId);

    List<Reservation> findByCustomerPhoneNumberAndDeletedFalse(String customerPhoneNumber);

    List<Reservation> findByCustomerPhoneNumberAndReservationTimeEndAfterAndDeletedFalse(String customerPhoneNumber, LocalDateTime currentTime);
}

package cz.inqool.tennisapp.domain.reservation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.court.id = :courtId AND r.isDeleted = false ORDER BY r.startTime ASC")
    List<Reservation> findByCourtIdOrderByStartTimeAsc(@Param("courtId") int courtId);

    @Query("SELECT r FROM Reservation r WHERE r.customer.phoneNumber = :phoneNumber AND r.isDeleted = false")
    List<Reservation> findByCustomerPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT r FROM Reservation r WHERE r.customer.phoneNumber = :phoneNumber AND r.startTime > :currentDate AND r.isDeleted = false")
    List<Reservation> findByCustomerPhoneNumberAndStartTimeAfter(@Param("phoneNumber") String phoneNumber, @Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.court.id = :courtId AND r.startTime < :endTime AND r.endTime > :startTime AND r.isDeleted = false")
    boolean existsByCourtIdAndTimeOverlap(@Param("courtId") int courtId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}

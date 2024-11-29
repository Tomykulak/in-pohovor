package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtService;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.customer.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private CourtService courtService;
    private CustomerService customerService;


    public ReservationService(ReservationRepository reservationRepository, CourtService courtService, CustomerService customerService) {
        this.reservationRepository = reservationRepository;
        this.courtService = courtService;
        this.customerService = customerService;
    }

    public Reservation getReservationById(int id){
        return reservationRepository.findById((long) id).orElse(null);
    }

    public List<Reservation> getReservationsForCourt(int courtId) {
        return reservationRepository.findByCourtIdOrderByStartTimeAsc(courtId);
    }

    public List<Reservation> getReservationsForPhoneNumber(String phoneNumber, boolean onlyFuture) {
        return onlyFuture
                ? reservationRepository.findByCustomerPhoneNumberAndStartTimeAfter(phoneNumber, LocalDateTime.now())
                : reservationRepository.findByCustomerPhoneNumber(phoneNumber);
    }

    public Reservation createReservation(int courtId, String customerName, String phoneNumber, LocalDateTime startTime, LocalDateTime endTime) {
        // Validate overlapping reservations
        if (reservationRepository.existsByCourtIdAndTimeOverlap(courtId, startTime, endTime)) {
            throw new IllegalArgumentException("Conflict with existing reservation");
        }

        // Find or create customer
        Customer customer = customerService.findOrCreateCustomer(customerName, phoneNumber);

        // Get court
        Court court = courtService.getCourtById(courtId)
                .orElseThrow(() -> new IllegalArgumentException("Court not found"));

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setCourt(court);
        reservation.setCustomer(customer);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }


    public void deleteReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById((long) reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        // soft delete
        reservation.setDeleted(true);
        reservationRepository.save(reservation);
    }
}

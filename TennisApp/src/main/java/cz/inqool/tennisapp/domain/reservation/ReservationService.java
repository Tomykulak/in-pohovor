package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtRepository;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;
    private CourtRepository courtRepository;
    private CustomerRepository customerRepository;


    public List<Reservation> getReservationsByCourtId(int courtId){
        courtRepository.findById((long) courtId)
                .orElseThrow(() -> new IllegalArgumentException("Court not found"));

        return reservationRepository.findByCourtIdAndDeletedFalse(courtId);
    }

    public List<Reservation> getReservationsByCustomerPhoneNumber(String customerPhoneNumber) {
        customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return reservationRepository.findByCustomerPhoneNumberAndDeletedFalse(customerPhoneNumber);
    }

    public List<Reservation> getUpcomingReservationsByCustomerPhoneNumber(String customerPhoneNumber) {
        customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return reservationRepository.findByCustomerPhoneNumberAndReservationTimeEndAfterAndDeletedFalse(customerPhoneNumber, LocalDateTime.now());
    }

    private double calculateTotalCost(double price, LocalDateTime startTime, LocalDateTime endTime, boolean isDoubles){
        double durationInMinutes = Duration.between(startTime, endTime).toMinutes();
        double totalCost = price * durationInMinutes;
        if (isDoubles) {
            totalCost *= 1.5;
        }
        return totalCost;
    }

    public double createReservation(int courtId, String customerName, String customerPhoneNumber, LocalDateTime startTime, LocalDateTime endTime, boolean isDoubles) {
        Court court = courtRepository.findById((long) courtId)
                .orElseThrow(() -> new IllegalArgumentException("Court not found"));
        List<Reservation> existingReservations = reservationRepository.findByCourtIdAndDeletedFalse(courtId);
        for (Reservation existingReservation : existingReservations) {
            if (startTime.isBefore(existingReservation.getEndTime()) && endTime.isAfter(existingReservation.getStartTime())) {
                throw new IllegalArgumentException("Not available at this time.");
            }
        }
        // create customer
        Customer customer = customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseGet(() -> {
                    Customer newCustomer = new Customer(customerName, customerPhoneNumber);
                    return customerRepository.save(newCustomer);
                });

        double totalCost = calculateTotalCost(court.getSurfaceType().getPricePerMinute(), startTime, endTime, isDoubles);
        Reservation reservation = new Reservation(court, customer, startTime, endTime, isDoubles, totalCost);

        reservationRepository.save(reservation);

        return totalCost;
    }

    public Reservation updateReservation(
            Long id,
            Long courtId,
            String customerName,
            String customerPhoneNumber,
            LocalDateTime startTime,
            LocalDateTime endTime,
            boolean isDoubles) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new IllegalArgumentException("Court not found"));
        Customer customer = customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseGet(() -> {
                    Customer newCustomer = new Customer(customerName, customerPhoneNumber);
                    return customerRepository.save(newCustomer);
                });

        double totalCost = calculateTotalCost(court.getSurfaceType().getPricePerMinute(), startTime, endTime, isDoubles);

        reservation.setCourt(court);
        reservation.setCustomer(customer);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setDoubles(isDoubles);
        reservation.setPrice(totalCost);

        return reservationRepository.save(reservation);
    }


    public void deleteReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById((long) reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        if (reservation.isDeleted()){
            throw new IllegalArgumentException("Reservation is already deleted");
        }

        reservation.setDeleted(true);
        reservationRepository.save(reservation);
    }
}

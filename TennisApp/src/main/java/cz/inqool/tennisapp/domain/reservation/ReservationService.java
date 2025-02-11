package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtRepository;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.customer.CustomerRepository;
import cz.inqool.tennisapp.utils.exceptions.AlreadyDeletedException;
import cz.inqool.tennisapp.utils.exceptions.BadRequestException;
import cz.inqool.tennisapp.utils.exceptions.ConflictException;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
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
                .orElseThrow(NotFoundException::new);

        return reservationRepository.findByCourtIdAndDeletedFalse(courtId);
    }

    public List<Reservation> getReservationsByCustomerPhoneNumber(String customerPhoneNumber) {
        customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseThrow(NotFoundException::new);

        return reservationRepository.findByCustomerPhoneNumberAndDeletedFalse(customerPhoneNumber);
    }

    public List<Reservation> getUpcomingReservationsByCustomerPhoneNumber(String customerPhoneNumber) {
        customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseThrow(NotFoundException::new);

        return reservationRepository.findByCustomerPhoneNumberAndEndTimeAfterAndDeletedFalse(customerPhoneNumber, LocalDateTime.now());
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
                .orElseThrow(NotFoundException::new);

        System.out.println("Court retrieved: " + court);
        if (court.getSurfaceType() == null) {
            System.out.println("Court's surfaceType is null!");
        } else {
            System.out.println("SurfaceType: " + court.getSurfaceType() +
                    ", Price per minute: " + court.getSurfaceType().getPricePerMinute());
        }

        if (court.isDeleted()) {
            throw new AlreadyDeletedException();
        }

        // Validate input data
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new BadRequestException();
        }
        if (customerPhoneNumber == null || customerPhoneNumber.trim().isEmpty()) {
            throw new BadRequestException();
        }
        if (startTime == null || endTime == null) {
            throw new BadRequestException();
        }

        List<Reservation> existingReservations = reservationRepository.findByCourtIdAndDeletedFalse(courtId);

        for (Reservation existingReservation : existingReservations) {
            if (endTime.isAfter(existingReservation.getStartTime()) && startTime.isBefore(existingReservation.getEndTime())) {
                throw new ConflictException();
            }
        }
        // create customer
        Customer customer = customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseGet(() -> {
                    Customer newCustomer = new Customer(customerName, customerPhoneNumber);
                    return customerRepository.save(newCustomer);
                });

        double totalCost = calculateTotalCost(court.getSurfaceType().getPricePerMinute(), startTime, endTime, isDoubles);
        System.out.println("Calculated total cost: " + totalCost);

        Reservation reservation = new Reservation(court, customer, startTime, endTime, isDoubles, totalCost);
        reservation.setSurfaceType(court.getSurfaceType());
        reservationRepository.save(reservation);

        return totalCost;
    }

    public Reservation updateReservation(
            int id,
            int courtId,
            String customerName,
            String customerPhoneNumber,
            LocalDateTime startTime,
            LocalDateTime endTime,
            boolean isDoubles) {

        Reservation reservation = reservationRepository.findById((long) id)
                .orElseThrow(NotFoundException::new);
        Court court = courtRepository.findById((long) courtId)
                .orElseThrow(NotFoundException::new);
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
                .orElseThrow(NotFoundException::new);

        if (reservation.isDeleted()) {
            throw new AlreadyDeletedException();
        }

        // soft delete
        reservation.setDeleted(true);
        reservationRepository.save(reservation);
    }

}

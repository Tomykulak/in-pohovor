package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtRepository;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.customer.CustomerRepository;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.utils.exceptions.AlreadyDeletedException;
import cz.inqool.tennisapp.utils.exceptions.BadRequestException;
import cz.inqool.tennisapp.utils.exceptions.ConflictException;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CourtRepository courtRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Court court;
    private Customer customer;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        court = new Court();
        court.setId(1);
        court.setSurfaceType(new SurfaceType("Grass", 5));

        customer = new Customer("tester", "123");

        reservation = new Reservation(
                court,
                customer,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(1),
                true,
                100.0
        );
    }

    @Test
    void getReservationByCourtIdNotDeleted() {
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(reservationRepository.findByCourtIdAndDeletedFalse(1)).thenReturn(Arrays.asList(reservation));

        List<Reservation> reservations = reservationService.getReservationsByCourtId(1);

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals("tester", reservations.get(0).getCustomer().getName());
        assertEquals("123", reservations.get(0).getCustomer().getPhoneNumber());
    }

    @Test
    void getReservationsByCourtId_NotFound() {
        when(courtRepository.findById(-1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservationService.getReservationsByCourtId(-1));
    }

    @Test
    void getReservationsByCustomerPhoneNumberNotDeleted() {
        when(customerRepository.findByPhoneNumber("123")).thenReturn(Optional.of(customer));
        when(reservationRepository.findByCustomerPhoneNumberAndDeletedFalse("123"))
                .thenReturn(Arrays.asList(reservation));
        List<Reservation> reservations = reservationService.getReservationsByCustomerPhoneNumber("123");

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals("tester", reservations.get(0).getCustomer().getName());
        assertEquals("123", reservations.get(0).getCustomer().getPhoneNumber());
    }

    @Test
    void getReservationsByCustomerPhoneNumber_NotFound() {
        when(customerRepository.findByPhoneNumber("123")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservationService.getReservationsByCustomerPhoneNumber("123"));
    }

    @Test
    void getUpcomingReservationsByCustomerPhoneNumberNotDeleted() {
        LocalDateTime currentTime = LocalDateTime.now();

        Reservation previousReservation = new Reservation(
                court, customer,
                currentTime.minusDays(1),
                currentTime.minusDays(1).plusHours(1),
                false,
                600.0
        );

        previousReservation.setCustomer(customer);

        when(customerRepository.findByPhoneNumber("123")).thenReturn(Optional.of(customer));
        when(reservationRepository.findByCustomerPhoneNumberAndEndTimeAfterAndDeletedFalse(
                "123456789", currentTime)).thenReturn(Arrays.asList());

        List<Reservation> reservations = reservationService.getUpcomingReservationsByCustomerPhoneNumber("123");

        assertNotNull(reservations);
        assertEquals(0, reservations.size());
    }

    @Test
    void getUpcomingReservationsByCustomerPhoneNumber_NotFound(){
        when(customerRepository.findByPhoneNumber("doesnt exist")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> reservationService.getUpcomingReservationsByCustomerPhoneNumber("123"));
    }

    @Test
    void createReservation() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);

        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(reservationRepository.findByCourtIdAndDeletedFalse(1)).thenReturn(Arrays.asList());
        when(customerRepository.findByPhoneNumber("123")).thenReturn(Optional.of(customer));

        double price = reservationService.createReservation(
                1,
                "tester",
                "123",
                startTime,
                endTime,
                false
        );

        verify(reservationRepository, times(1)).save(any(Reservation.class));
        assertEquals(300.0, price);
        assertEquals("tester", reservation.getCustomer().getName());
        assertEquals("123", reservation.getCustomer().getPhoneNumber());
    }

    @Test
    void createReservation_NotFound() {
        when(courtRepository.findById(-1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                reservationService.createReservation(
                        1,
                        "tester",
                        "123",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        false
                )
        );
    }

    @Test
    void createReservation_AlreadyDeleted() {
        Court court = new Court();
        court.setDeleted(true);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));

        assertThrows(AlreadyDeletedException.class, () ->
                reservationService.createReservation(
                        1,
                        "tester",
                        "123",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        false
                )
        );
    }

    @Test
    void createReservation_InvalidCustomerName() {
        Court court = new Court();
        court.setDeleted(false);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));

        assertThrows(BadRequestException.class, () ->
                reservationService.createReservation(
                        1,
                        "",
                        "987654321",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        false
                )
        );
    }

    @Test
    void createReservation_InvalidCustomerPhoneNumber() {
        // Arrange
        Court court = new Court();
        court.setDeleted(false);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));

        // Act & Assert
        assertThrows(BadRequestException.class, () ->
                reservationService.createReservation(
                        1,
                        "tester",
                        "",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        false
                )
        );
    }
    @Test
    void createReservationOverlappingReservation_ConflictException() {
        Court court = new Court();
        court.setDeleted(false);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));

        Reservation existingReservation = new Reservation();
        existingReservation.setStartTime(LocalDateTime.now().plusMinutes(30));
        existingReservation.setEndTime(LocalDateTime.now().plusHours(2));

        when(reservationRepository.findByCourtIdAndDeletedFalse(1))
                .thenReturn(List.of(existingReservation));

        assertThrows(ConflictException.class, () ->
                reservationService.createReservation(
                        1,
                        "tester",
                        "123",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        false
                )
        );
    }

    @Test
    void updateReservation() {
        LocalDateTime newStartTime = LocalDateTime.now().plusDays(2);
        LocalDateTime newEndTime = newStartTime.plusHours(1);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));

        reservationService.updateReservation(
                1,
                1,
                "tester",
                "123",
                newStartTime,
                newEndTime,
                false);

        verify(reservationRepository, times(1)).save(reservation);
        assertEquals(newStartTime, reservation.getStartTime());
        assertFalse(reservation.isDoubles());
    }

    @Test
    void updateReservation_ReservationNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                reservationService.updateReservation(1, 1, "tester", "123",
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(2).plusHours(1),
                        false)
        );
    }

    @Test
    void updateReservation_CourtNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(courtRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                reservationService.updateReservation(
                        1,
                        1,
                        "tester",
                        "123",
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(2).plusHours(1),
                        false
                )
        );
    }

    @Test
    void deleteReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        reservationService.deleteReservation(1);

        assertTrue(reservation.isDeleted());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void deleteReservation_NotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservationService.deleteReservation(1));
    }

    @Test
    void deleteReservation_AlreadyDeleted() {
        Reservation reservation = new Reservation();
        reservation.setDeleted(true);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThrows(AlreadyDeletedException.class, () -> reservationService.deleteReservation(1));
    }


}

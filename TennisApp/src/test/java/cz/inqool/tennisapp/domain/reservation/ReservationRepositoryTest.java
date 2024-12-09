package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtRepository;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.customer.CustomerRepository;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ReservationRepositoryTest {

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

    
}

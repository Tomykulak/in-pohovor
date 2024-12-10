package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtRepository;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.customer.CustomerRepository;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    SurfaceTypeRepository surfaceTypeRepository;

    @Autowired
    CourtRepository courtRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CustomerRepository customerRepository;

    SurfaceType surfaceType;

    Court court;

    Customer customer1;
    Customer customer2;

    Reservation reservation1;
    Reservation reservation2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        surfaceType = new SurfaceType(
                "Grass",
                5
        );
        surfaceTypeRepository.save(surfaceType);

        court = new Court(
                "court1",
                surfaceType
        );
        courtRepository.save(court);

        customer1 = new Customer(
                "tester1",
                "123"
        );
        customer2 = new Customer(
                "tester2",
                "321"
        );
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        reservation1 = new Reservation(
                court,
                customer1,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                true,
                600.0
        );
        reservation2 = new Reservation(
                court,
                customer2,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2),
                true,
                600.0
        );
    }

    @Test
    void findByCourtIdAndDeletedFalse(){
        reservation1.setDeleted(false);
        reservation2.setDeleted(true);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        int courtId = court.getId();

        List<Reservation> reservations = reservationRepository.findByCourtIdAndDeletedFalse(courtId);

        assertEquals(1, reservations.size());
        assertFalse(reservations.get(0).isDeleted());
    }

    @Test
    void findByCustomerPhoneNumberAndDeletedFalse(){
        reservation1.setDeleted(false);
        reservationRepository.save(reservation1);

        List<Reservation> reservations = reservationRepository.findByCustomerPhoneNumberAndDeletedFalse(customer1.getPhoneNumber());

        assertEquals(1, reservations.size());
        assertFalse(reservations.get(0).isDeleted());
    }

    @Test
    void findByCustomerPhoneNumberAndReservationTimeEndAfterDeleteFalse(){
        reservation1.setDeleted(false);
        reservationRepository.save(reservation1);

        List<Reservation> reservations = reservationRepository.findByCustomerPhoneNumberAndEndTimeAfterAndDeletedFalse(customer1.getPhoneNumber(), LocalDateTime.now());
        assertEquals(1, reservations.size());
    }
}

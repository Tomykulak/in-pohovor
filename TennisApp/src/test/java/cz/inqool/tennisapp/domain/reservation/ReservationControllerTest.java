package cz.inqool.tennisapp.domain.reservation;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.customer.Customer;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @Test
    public void getReservationsByCourtId() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5);
        Court court = new Court("Grass court 1", grass);
        Customer customer = new Customer("tester", "123");

        Reservation reservation1 = new Reservation(court, customer, LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, 100.0);
        Reservation reservation2 = new Reservation(court, customer, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), false, 150.0);

        when(reservationService.getReservationsByCourtId(1)).thenReturn(List.of(reservation1, reservation2));

        mockMvc.perform(get("/api/reservation/court/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].price").value(100.0))
                .andExpect(jsonPath("$.items[1].price").value(150.0))
                .andExpect(jsonPath("$.items[0].customerName").value("tester"))
                .andExpect(jsonPath("$.items[1].customerName").value("tester"));
    }


    @Test
    public void getReservationsByCustomerPhoneNumber() throws Exception {
        SurfaceType carpet = new SurfaceType("Carpet", 10);
        Court court = new Court("Carpet court 1", carpet);
        Customer customer = new Customer("Tester", "123");

        Reservation reservation = new Reservation(court, customer, LocalDateTime.now(), LocalDateTime.now().plusHours(1), true, 200.0);

        when(reservationService.getReservationsByCustomerPhoneNumber("123")).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservation/customer/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].price").value(200.0))
                .andExpect(jsonPath("$.items[0].customerName").value("Tester"))
                .andExpect(jsonPath("$.items[0].courtName").value("Carpet court 1"));
    }


    @Test
    public void createReservation() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 11, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 12, 11, 11, 0);

        when(reservationService.createReservation(1, "tester", "123", startTime, endTime, false))
                .thenReturn(250.0);

        mockMvc.perform(post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"courtId\":1,\"customerName\":\"tester\",\"phoneNumber\":\"123\",\"startTime\":\"2024-12-11T10:00:00\",\"endTime\":\"2024-12-11T11:00:00\",\"isDoubles\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value(250.0));
    }


    @Test
    public void updateReservation() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5);
        Court court = new Court("Grass court 1", grass);
        Customer customer = new Customer("Tester", "123");

        Reservation updatedReservation = new Reservation(court, customer, LocalDateTime.of(2024, 12, 11, 10, 0), LocalDateTime.of(2024, 12, 11, 12, 0), true, 300.0);

        when(reservationService.updateReservation(
                Mockito.eq(1),
                Mockito.eq(1),
                Mockito.eq("Tester"),
                Mockito.eq("123"),
                Mockito.eq(LocalDateTime.of(2024, 12, 11, 10, 0)),
                Mockito.eq(LocalDateTime.of(2024, 12, 11, 12, 0)),
                Mockito.eq(true)
        )).thenReturn(updatedReservation);

        mockMvc.perform(put("/api/reservation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"courtId\":1,\"customerName\":\"Tester\",\"phoneNumber\":\"123\",\"startTime\":\"2024-12-11T10:00:00\",\"endTime\":\"2024-12-11T12:00:00\",\"isDoubles\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.price").value(300.0))
                .andExpect(jsonPath("$.content.customerName").value("Tester"))
                .andExpect(jsonPath("$.content.courtName").value("Grass court 1"));
    }


    @Test
    public void deleteReservation() throws Exception {
        mockMvc.perform(delete("/api/reservation/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getReservationsByCourtId_NotFound() throws Exception {
        when(reservationService.getReservationsByCourtId(99)).thenThrow(new NotFoundException());

        mockMvc.perform(get("/api/reservation/court/99"))
                .andExpect(status().isNotFound());
    }
}

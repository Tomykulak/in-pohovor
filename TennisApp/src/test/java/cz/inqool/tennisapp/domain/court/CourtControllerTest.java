package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourtController.class)
public class CourtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourtService courtService;

    @Test
    public void getAllCourts() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5);
        SurfaceType carpet = new SurfaceType("Carpet", 10);
        Court court1 = new Court("Grass court 1", grass);
        Court court2 = new Court("Carpet court 1", carpet);

        when(courtService.getAllCourts()).thenReturn(List.of(court1, court2));

        mockMvc.perform(get("/api/court"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].name").value("Grass court 1"))
                .andExpect(jsonPath("$.items[1].name").value("Carpet court 1"));
    }

    @Test
    public void getCourtById() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5);
        Court court = new Court("Grass court 1", grass);

        when(courtService.getCourtById(1)).thenReturn(court);

        mockMvc.perform(get("/api/court/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.name").value("Grass court 1"));
    }

    @Test
    public void getCourtById_NotFound() throws Exception {
        when(courtService.getCourtById(1)).thenThrow(new NotFoundException());

        mockMvc.perform(get("/api/court/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCourt() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5);
        Court court = new Court("Grass court 1", grass);

        when(courtService.createCourt(Mockito.any(Court.class))).thenReturn(court);

        mockMvc.perform(post("/api/court")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Grass court 1\",\"surfaceType\":{\"name\":\"Grass\",\"pricePerMinute\":5.0}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content.name").value("Grass court 1"));
    }

    @Test
    public void createCourt_NotFound() throws Exception {
        mockMvc.perform(post("/api/court")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"surfaceType\":null}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCourt() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5);
        Court court = new Court("Grass court 1", grass);

        when(courtService.updateCourt(Mockito.eq(1), Mockito.any(Court.class))).thenReturn(court);

        mockMvc.perform(put("/api/court/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Grass court 1\",\"surfaceType\":{\"name\":\"Grass\",\"pricePerMinute\":5.0}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.name").value("Grass court 1"))
                .andExpect(jsonPath("$.content.surfaceType").value("Grass"))
                .andExpect(jsonPath("$.content.surfaceCost").value(5.0));
    }

    @Test
    public void updateCourt_NotFound() throws Exception {
        when(courtService.updateCourt(Mockito.eq(1), Mockito.any(Court.class))).thenThrow(new NotFoundException());

        mockMvc.perform(put("/api/court/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Grass court 1\",\"surfaceType\":{\"name\":\"Grass\",\"pricePerMinute\":5.0}}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCourtById() throws Exception {

        mockMvc.perform(delete("/api/court/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCourtById_NotFound() throws Exception {
        Mockito.doThrow(new NotFoundException()).when(courtService).deleteCourtById(1);

        mockMvc.perform(delete("/api/court/1"))
                .andExpect(status().isNotFound());
    }
}

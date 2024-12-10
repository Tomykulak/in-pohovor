package cz.inqool.tennisapp.domain.surfaceType;

import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
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

@WebMvcTest(SurfaceTypeController.class)
@ExtendWith(SpringExtension.class)
public class SurfaceTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SurfaceTypeService surfaceTypeService;

    @Test
    public void getAllSurfaceTypes() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5.0);
        SurfaceType carpet = new SurfaceType("Carpet", 10.0);

        when(surfaceTypeService.getAllSurfaceTypes()).thenReturn(List.of(grass, carpet));

        mockMvc.perform(get("/api/surface"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].surfaceType.name").value("Grass"))
                .andExpect(jsonPath("$.items[0].surfaceType.pricePerMinute").value(5.0))
                .andExpect(jsonPath("$.items[1].surfaceType.name").value("Carpet"))
                .andExpect(jsonPath("$.items[1].surfaceType.pricePerMinute").value(10.0));
    }

    @Test
    public void createSurfaceType() throws Exception {
        SurfaceType grass = new SurfaceType("Grass", 5.0);

        when(surfaceTypeService.createSurfaceType(Mockito.any(SurfaceType.class))).thenReturn(grass);

        mockMvc.perform(post("/api/surface")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Grass\",\"pricePerMinute\":5.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content.surfaceType.name").value("Grass"))
                .andExpect(jsonPath("$.content.surfaceType.pricePerMinute").value(5.0));
    }

    @Test
    public void updateSurfaceType() throws Exception {
        SurfaceType grass = new SurfaceType("Grass Updated", 6.0);

        when(surfaceTypeService.updateSurfaceType(Mockito.eq(1), Mockito.any(SurfaceType.class))).thenReturn(grass);

        mockMvc.perform(put("/api/surface/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Grass Updated\",\"pricePerMinute\":6.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.surfaceType.name").value("Grass Updated"))
                .andExpect(jsonPath("$.content.surfaceType.pricePerMinute").value(6.0));
    }

    @Test
    public void deleteSurfaceType() throws Exception {
        mockMvc.perform(delete("/api/surface/1"))
                .andExpect(status().isNoContent());
    }
}

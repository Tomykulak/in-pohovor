package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourtServiceTest {
    @Mock
    private CourtRepository courtRepository;

    @InjectMocks
    private CourtService courtService;

    SurfaceType surfaceType;
    Court court;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        surfaceType = new SurfaceType("Grass", 5);
        court = new Court("Grass court 1", surfaceType);
    }

    @Test
    void createCourt() {
        when(courtRepository.save(any(Court.class))).thenReturn(court);

        System.out.println(court.getName());
        System.out.println(court.getSurfaceType());

        Court existingCourt = courtService.createCourt(court);

        assertNotNull(existingCourt);
        assertEquals(court.getName(), existingCourt.getName());
        verify(courtRepository, times(1)).save(court);
    }
}

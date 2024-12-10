package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.utils.exceptions.AlreadyDeletedException;
import cz.inqool.tennisapp.utils.exceptions.BadRequestException;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void getAllCourts() {
        Court court2 = new Court("Grass court 2", surfaceType);
        when(courtRepository.findByDeletedFalse()).thenReturn(List.of(court, court2));

        List<Court> courts = courtService.getAllCourts();

        assertNotNull(courts, "The list of courts should not be null.");
        assertEquals(2, courts.size(), "The list of courts should contain 2 items.");
        assertEquals("Grass court 1", courts.get(0).getName(), "The first court name should match.");
        assertEquals("Grass court 2", courts.get(1).getName(), "The second court name should match.");
        verify(courtRepository, times(1)).findByDeletedFalse();
    }

    @Test
    void getCourtById() {
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));

        Court existingCourt = courtService.getCourtById(1);

        assertNotNull(existingCourt, "The court should not be null.");
        assertEquals("Grass court 1", existingCourt.getName(), "The court name should match.");
        assertEquals(surfaceType, existingCourt.getSurfaceType(), "The surface type should match.");
        verify(courtRepository, times(1)).findById(1L);
    }

    @Test
    void getCourtById_NotFound() {
        when(courtRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courtService.getCourtById(1));
    }



    @Test
    void createCourt() {
        when(courtRepository.save(any(Court.class))).thenReturn(court);

        Court existingCourt = courtService.createCourt(court);

        assertNotNull(existingCourt);
        assertEquals(court.getName(), existingCourt.getName());
        verify(courtRepository, times(1)).save(court);
    }

    @Test
    void createCourt_InvalidSurfaceType() {
        court.setSurfaceType(null);

        assertThrows(BadRequestException.class, () -> courtService.createCourt(court));
    }

    @Test
    void createCourt_InvalidName() {
        court.setName("");

        assertThrows(BadRequestException.class, () -> courtService.createCourt(court));
    }

    @Test
    void updateCourt_NotFound() {
        when(courtRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                courtService.updateCourt(1, court)
        );
    }

    @Test
    void updateCourt_AlreadyDeleted() {
        court.setDeleted(true);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));

        assertThrows(AlreadyDeletedException.class, () ->
                courtService.updateCourt(1, court)
        );
    }

    @Test
    void updateCourt_InvalidName() {
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        court.setDeleted(false);

        Court updatedCourt = new Court();
        updatedCourt.setName("");
        updatedCourt.setSurfaceType(surfaceType);

        assertThrows(BadRequestException.class, () ->
                courtService.updateCourt(1, updatedCourt)
        );
    }

    @Test
    void updateCourt_NullSurfaceType() {
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        court.setDeleted(false);

        Court updatedCourt = new Court();
        updatedCourt.setName("Updated Court");
        updatedCourt.setSurfaceType(null);

        assertThrows(BadRequestException.class, () ->
                courtService.updateCourt(1, updatedCourt)
        );
    }

    @Test
    void deleteCourtById() {
        Court courtToDelete = new Court("Grass court 1", surfaceType);
        courtToDelete.setDeleted(false);

        when(courtRepository.findById(1L)).thenReturn(Optional.of(courtToDelete));

        courtService.deleteCourtById(1);

        assertTrue(courtToDelete.isDeleted(), "The court should be marked as deleted.");
        verify(courtRepository, times(1)).save(courtToDelete);
    }

    @Test
    void deleteCourtById_NotFound() {
        when(courtRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courtService.deleteCourtById(1));
    }

    @Test
    void deleteCourtById_AlreadyDeleted() {
        Court deletedCourt = new Court("Grass court 1", surfaceType);
        deletedCourt.setDeleted(true);

        when(courtRepository.findById(1L)).thenReturn(Optional.of(deletedCourt));

        assertThrows(AlreadyDeletedException.class, () -> courtService.deleteCourtById(1));
    }


}

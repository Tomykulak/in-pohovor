package cz.inqool.tennisapp.domain.surfaceType;

import cz.inqool.tennisapp.utils.exceptions.BadRequestException;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SurfaceTypeServiceTest {
    @Mock
    private SurfaceTypeRepository surfaceTypeRepository;

    @InjectMocks
    private SurfaceTypeService surfaceTypeService;

    private SurfaceType surfaceType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        surfaceType = new SurfaceType();
        surfaceType.setId(1);
        surfaceType.setName("Grass");
        surfaceType.setPricePerMinute(5.0);
        surfaceType.setDeleted(false);
    }

    @Test
    void createSurfaceType() {
        when(surfaceTypeRepository.save(surfaceType)).thenReturn(surfaceType);
        SurfaceType createdSurfaceType = surfaceTypeService.createSurfaceType(surfaceType);

        assertNotNull(createdSurfaceType, "The created surface type should not be null.");
        assertEquals(surfaceType.getName(), createdSurfaceType.getName(), "The surface type name should match.");
        verify(surfaceTypeRepository, times(1)).save(surfaceType);
    }

    @Test
    void createSurfaceType_InvalidName() {
        surfaceType.setName(null);
        assertThrows(BadRequestException.class, () -> surfaceTypeService.createSurfaceType(surfaceType));
    }

    @Test
    void deleteSurfaceType() {
        when(surfaceTypeRepository.findById(1L)).thenReturn(Optional.of(surfaceType));

        surfaceTypeService.deleteSurfaceType(1);

        assertTrue(surfaceType.isDeleted(), "The surface type should be marked as deleted.");
        verify(surfaceTypeRepository, times(1)).save(surfaceType);
    }

    @Test
    void deleteSurfaceType_NotFound() {
        when(surfaceTypeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> surfaceTypeService.deleteSurfaceType(1));
    }
}
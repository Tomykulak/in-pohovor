package cz.inqool.tennisapp.domain.surfaceType;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SurfaceTypeService {
    private final SurfaceTypeRepository surfaceTypeRepository;

    public List<SurfaceType> getAllSurfaceTypes() {
        return surfaceTypeRepository.findByDeletedFalse();
    }

    public SurfaceType createSurfaceType(SurfaceType surfaceType) {
        if (surfaceType.getName() == null) {
            throw new IllegalArgumentException("surfaceType name is null");
        }

        return surfaceTypeRepository.save(surfaceType);
    }

    public void deleteSurfaceType(int surfaceTypeId) {
        SurfaceType surface = surfaceTypeRepository.findById((long) surfaceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("SurfaceType not found"));

        if (surface.isDeleted()){
            throw new IllegalArgumentException("SurfaceType already deleted");
        }

        surface.setDeleted(true);
        surfaceTypeRepository.save(surface);
    }

    public SurfaceType updateSurfaceType(int surfaceTypeId, SurfaceType surfaceType) throws IllegalArgumentException {
        SurfaceType existingSurfaceType = surfaceTypeRepository.findById((long) surfaceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("SurfaceType not found"));
        if (surfaceType.getName() == null) {
            throw new IllegalArgumentException("SurfaceType name cannot be null");
        }

        existingSurfaceType.setName(surfaceType.getName());
        existingSurfaceType.setPricePerMinute(surfaceType.getPricePerMinute());
        return surfaceTypeRepository.save(existingSurfaceType);
    }
}

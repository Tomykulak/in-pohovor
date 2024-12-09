package cz.inqool.tennisapp.domain.surfaceType;

import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/surface")
@AllArgsConstructor
@Tag(name = "Surfaces", description = "Manage surfaces.")
public class SurfaceTypeController {
    private final SurfaceTypeService surfaceTypeService;

    @GetMapping("")
    @Operation(summary = "Retrieve all surface types.", description = "Fetch a list of all surface types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved surface types.")
    })
    public ArrayResponse<SurfaceTypeResponse> getAllSurfaceTypes() {
        List<SurfaceType> surfaceTypes = surfaceTypeService.getAllSurfaceTypes();
        return ArrayResponse.of(surfaceTypes, SurfaceTypeResponse::new);
    }

    @PostMapping("")
    public ObjectResponse<SurfaceTypeResponse> createSurfaceType(@Valid @RequestBody SurfaceType surfaceType){
        return ObjectResponse.of(surfaceTypeService.createSurfaceType(surfaceType), SurfaceTypeResponse::new);
    }

    @PutMapping("/{id}")
    public ObjectResponse<SurfaceTypeResponse> updateSurfaceType(@PathVariable int id, @Valid @RequestBody SurfaceType surfaceType){
        return ObjectResponse.of(surfaceTypeService.updateSurfaceType(id, surfaceType), SurfaceTypeResponse::new);
    }

    @DeleteMapping("/{id}")
    public void deleteSurfaceType(@PathVariable int id){
        surfaceTypeService.deleteSurfaceType(id);
    }

}

package cz.inqool.tennisapp.domain.surfaceType;

import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new surface type", description = "Add a new surface type to the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Surface type created successfully.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurfaceTypeResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    public ObjectResponse<SurfaceTypeResponse> createSurfaceType(@Valid @RequestBody SurfaceType surfaceType) {
        return ObjectResponse.of(surfaceTypeService.createSurfaceType(surfaceType), SurfaceTypeResponse::new);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update surface type by ID", description = "Update the details of a surface type by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Surface type updated successfully.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurfaceTypeResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data."),
        @ApiResponse(responseCode = "404", description = "Surface type not found."),
        @ApiResponse(responseCode = "409", description = "Conflict occurred while updating the surface type.")
    })
    public ObjectResponse<SurfaceTypeResponse> updateSurfaceType(@PathVariable int id, @Valid @RequestBody SurfaceType surfaceType) {
        return ObjectResponse.of(surfaceTypeService.updateSurfaceType(id, surfaceType), SurfaceTypeResponse::new);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete surface type by ID", description = "Delete a surface type by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Surface type deleted successfully."),
        @ApiResponse(responseCode = "404", description = "Surface type not found."),
        @ApiResponse(responseCode = "409", description = "Surface type cannot be deleted due to conflicts.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSurfaceType(@PathVariable int id) {
        surfaceTypeService.deleteSurfaceType(id);
    }
}

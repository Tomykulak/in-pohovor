package cz.inqool.tennisapp.domain.surfaceType;

import cz.inqool.tennisapp.utils.response.ArrayResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

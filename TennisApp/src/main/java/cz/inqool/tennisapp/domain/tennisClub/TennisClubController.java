package cz.inqool.tennisapp.domain.tennisClub;

import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tennis-club")
@Tag(name = "Tennis clubs", description = "Show tennis clubs with courts .")
public class TennisClubController {
    private TennisClubService tennisClubService;

    public TennisClubController(TennisClubService tennisClubService) {
        this.tennisClubService = tennisClubService;
    }

    @Operation(summary = "Retrieve all tennis clubs.", description = "Fetch a list of all tennis clubs with courts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved courts.")
    })
    @GetMapping(value = "", produces = "application/json")
    public ArrayResponse<TennisClubResponse> getAllTennisClubs(){
        List<TennisClub> tennisClubs = tennisClubService.getAllTennisClubs();
        return ArrayResponse.of(tennisClubs, TennisClubResponse::new);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve tennis club by ID.", description = "Fetch details of a specific tennis club ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tennis club."),
            @ApiResponse(responseCode = "404", description = "Tennis club not found.")
    })
    public ObjectResponse<TennisClubResponse> getTennisClubById(@PathVariable int id) {
        TennisClub tennisClub = tennisClubService.getTennisClubById(id);
        return ObjectResponse.of(tennisClub, TennisClubResponse::new);
    }
}

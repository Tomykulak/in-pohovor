package cz.inqool.tennisapp.domain.tennisClub;

import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import cz.inqool.tennisapp.utils.response.ArrayResponse;
import cz.inqool.tennisapp.utils.response.ObjectResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tennis-club")
public class TennisClubController {
    private TennisClubService tennisClubService;

    public TennisClubController(TennisClubService tennisClubService) {
        this.tennisClubService = tennisClubService;
    }

    @GetMapping(value = "", produces = "application/json")
    public ArrayResponse<TennisClubResponse> getAllTennisClubs(){
        List<TennisClub> tennisClubs = tennisClubService.getAllTennisClubs();
        return ArrayResponse.of(tennisClubs, TennisClubResponse::new);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ObjectResponse<TennisClubResponse> getTennisClubById(@PathVariable int id) {
        TennisClub tennisClub = tennisClubService.getTennisClubById(id);
        if (tennisClub == null) {
            throw new NotFoundException();
        }
        return ObjectResponse.of(tennisClub, TennisClubResponse::new);
    }
}

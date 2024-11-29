package cz.inqool.tenisapp.domain.tenisClub;

import cz.inqool.tenisapp.utils.exceptions.NotFoundException;
import cz.inqool.tenisapp.utils.response.ArrayResponse;
import cz.inqool.tenisapp.utils.response.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenis-club")
public class TenisClubController {
    private TenisClubService tenisClubService;

    @Autowired
    public TenisClubController(TenisClubService tenisClubService) {
        this.tenisClubService = tenisClubService;
    }

    @GetMapping(value = "", produces = "application/json")
    public ArrayResponse<TenisClubResponse> getAllTenisClubs(){
        List<TenisClub> tenisClubs = tenisClubService.getAllTenisClubs();
        return ArrayResponse.of(tenisClubs, TenisClubResponse::new);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ObjectResponse<TenisClubResponse> getTenisClubById(@PathVariable int id) {
        TenisClub tenisClub = tenisClubService.getTenisClubById(id);
        if (tenisClub == null) {
            throw new NotFoundException();
        }
        return ObjectResponse.of(tenisClub, TenisClubResponse::new);
    }
}

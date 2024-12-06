package cz.inqool.tennisapp.domain.tennisClub;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TennisClubService {
    private final TennisClubRepository tennisClubRepository;


    public TennisClubService(TennisClubRepository tennisClubRepository) {
        this.tennisClubRepository = tennisClubRepository;
    }

    public TennisClub getTennisClubById(int id) {
        return tennisClubRepository.findById((long) id)
                .orElseThrow(NotFoundException::new);
    }

    public List<TennisClub> getAllTennisClubs() {
        List<TennisClub> tennisClubs = new ArrayList<>();
        tennisClubRepository.findAll().forEach(tennisClubs::add);
        return tennisClubs;
    }

}

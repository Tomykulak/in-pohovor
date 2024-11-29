package cz.inqool.tennisapp.domain.tennisClub;

import cz.inqool.tennisapp.domain.court.Court;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TennisClubService {
    private final TennisClubRepository tennisClubRepository;


    @Autowired
    public TennisClubService(TennisClubRepository tennisClubRepository) {
        this.tennisClubRepository = tennisClubRepository;
    }

    public TennisClub getTennisClubById(int id) {
        return tennisClubRepository.findById((long) id).orElse(null);
    }

    public List<TennisClub> getAllTennisClubs() {
        List<TennisClub> tennisClubs = new ArrayList<>();
        tennisClubRepository.findAll().forEach(tennisClubs::add);
        return tennisClubs;
    }

    public List<Court> getAllCourts() {
        List<Court> courts = new ArrayList<>();
        tennisClubRepository.findAll();
        return courts;
    }
}

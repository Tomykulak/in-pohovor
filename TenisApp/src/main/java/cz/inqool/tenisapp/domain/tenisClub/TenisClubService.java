package cz.inqool.tenisapp.domain.tenisClub;

import cz.inqool.tenisapp.domain.court.Court;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TenisClubService {
    private final TenisClubRepository tenisClubRepository;


    @Autowired
    public TenisClubService(TenisClubRepository tenisClubRepository) {
        this.tenisClubRepository = tenisClubRepository;
    }

    public TenisClub getTenisClubById(int id) {
        return tenisClubRepository.findById((long) id).orElse(null);
    }

    public List<TenisClub> getAllTenisClubs() {
        List<TenisClub> tenisClubs = new ArrayList<>();
        tenisClubRepository.findAll().forEach(tenisClubs::add);
        return tenisClubs;
    }

    public List<Court> getAllCourts() {
        List<Court> courts = new ArrayList<>();
        tenisClubRepository.findAll();
        return courts;
    }
}

package cz.inqool.tenisapp.domain.court;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse {
    private int id;
    private String name;

    public CourtResponse(Court court) {
        this.id = court.getId();
        this.name = court.getName();
    }
}

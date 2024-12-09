package cz.inqool.tennisapp.domain.surfaceType;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SurfaceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotNull
    private double pricePerMinute;

    private boolean deleted = false;

    public SurfaceType(String name, double pricePerMinute) {
        this.name = name;
        this.pricePerMinute = pricePerMinute;
    }
}

package cz.inqool.tennisapp.domain.surfaceType;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SurfaceType {
    CLAY(250),
    GRASS(300),
    CARPET(400);

    private final int cost;
}

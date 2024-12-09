package cz.inqool.tennisapp.utils.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public class ArrayResponse<T> {

    List<T> items;

    // factory method
    static public <I, T> ArrayResponse<T> of(List<I> items, Function<I, T> mapper) {
        List<T> responses = items.stream()
                .map(mapper)
                .toList();
        return new ArrayResponse<>(responses, responses.size());
    }

    int count;

}

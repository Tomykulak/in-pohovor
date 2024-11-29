package cz.inqool.tennisapp.domain.customer;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerRequest {
    private String name;
    private String phoneNumber;
}

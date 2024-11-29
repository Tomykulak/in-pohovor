package cz.inqool.tennisapp.domain.customer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerResponse {
    private int id;
    private String name;
    private String phoneNumber;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
    }
}

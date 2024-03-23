package skylab.skymerch.entities.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skylab.skymerch.entities.Address;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestOrderDto {

    private int id;
    private float totalPrice;
    private String status;
    private int userId;
    private int addressId;
    private int paymentId;

    private Address address;
    private String orderNumber;


}

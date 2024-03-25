package skylab.skymerch.entities.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skylab.skymerch.entities.Address;
import skylab.skymerch.entities.Product;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestOrderDto {

    private float totalPrice;
    private String status;
    private int userId;
    private int addressId;
    private int paymentId;
    private List<Integer> products;
    private String orderNumber;


}

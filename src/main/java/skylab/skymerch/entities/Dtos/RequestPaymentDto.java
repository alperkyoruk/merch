package skylab.skymerch.entities.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestPaymentDto {
    private int id;
    private String status;
    private Date timePaid;
    private String type;
    private float amount;
    private int orderId;
    private int userId;

}

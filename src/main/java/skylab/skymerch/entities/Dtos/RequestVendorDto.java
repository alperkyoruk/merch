package skylab.skymerch.entities.Dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestVendorDto {
        private String vendorName;
        private int userId;

}

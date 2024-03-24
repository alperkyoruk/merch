package skylab.skymerch.entities.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestAddressDto {
        private int id;
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
        private int userId;
        private String address;
}

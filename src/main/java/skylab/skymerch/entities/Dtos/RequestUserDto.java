package skylab.skymerch.entities.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skylab.skymerch.entities.Role;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestUserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phone;


}

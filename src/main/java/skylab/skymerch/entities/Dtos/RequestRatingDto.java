package skylab.skymerch.entities.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestRatingDto {
    private int id;
    private int rating;
    private String comment;
    private int productId;
    private int userId;
}

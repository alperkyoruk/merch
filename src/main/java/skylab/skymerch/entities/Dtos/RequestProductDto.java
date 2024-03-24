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
public class RequestProductDto {
    private int id;
    private String name;
    private String description;
    private float price;
    private int stock;
    private String image;
    private Date createdAt;
    private Date updatedAt;
    private boolean discounted;
    private float discountRate;
    private int categoryId;
    private int subcategoryId;
    private int vendorId;
    private int rating;

}

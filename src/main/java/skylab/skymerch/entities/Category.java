package skylab.skymerch.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<Product> product;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<SubCategory> subCategory;

}

package skylab.skymerch.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "is_discounted")
    private boolean discounted;

    @Column(name = "discount_rate")
    private float discountRate;

    @Column(name = "average_rating")
    private float averageRating;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, targetEntity = Subcategory.class)
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Vendor> vendors;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH, targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Rating> ratings;

}

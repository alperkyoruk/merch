package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Rating;

public interface RatingDao extends JpaRepository<Rating, Integer> {
    Rating findById(int id);
    Rating findByProductId(int productId);
    Rating findByUserId(int userId);
    Rating findByRating(int rating);

}

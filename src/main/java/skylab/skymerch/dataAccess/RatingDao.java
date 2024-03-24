package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Rating;

import java.util.List;

public interface RatingDao extends JpaRepository<Rating, Integer> {
    Rating findById(int id);
    List<Rating> findAllByProductId(int productId);
    List<Rating> findAllByUserId(int userId);
    List<Rating> findAllByRating(int rating);

}

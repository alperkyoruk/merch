package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestRatingDto;
import skylab.skymerch.entities.Rating;

import java.util.List;

public interface RatingService {
    Result addRating(RequestRatingDto requestRatingDto);
    Result deleteRating(int ratingId);
    DataResult<Rating> getById(int ratingId);

    DataResult <List<Rating>> getRatings();
    DataResult<List<Rating>> getRatingsByProductId(int productId);
    DataResult<List<Rating>> getRatingsByUserId(int userId);

    DataResult<Float> getAverageRatingByProductId(int productId);

}

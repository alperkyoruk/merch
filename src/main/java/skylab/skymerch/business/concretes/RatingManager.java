package skylab.skymerch.business.concretes;

import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.RatingService;
import skylab.skymerch.business.constants.RatingMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.RatingDao;
import skylab.skymerch.entities.Rating;

import java.util.List;

@Service
public class RatingManager implements RatingService {

    private RatingDao ratingDao;

    public RatingManager(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public Result addRating(Rating rating) {
        if(rating.getRating() == 0 || rating.getProduct().getId() == 0 || rating.getUser().getId() == 0){
            return new ErrorResult(RatingMessages.RatingCannotBeNull);
        }

        ratingDao.save(rating);
        return new SuccessResult(RatingMessages.RatingAdded);
    }

    @Override
    public Result deleteRating(int ratingId) {
        var result = getById(ratingId);
        if(!result.isSuccess()){
            return new ErrorResult(RatingMessages.RatingCannotBeFound);
        }

        var rating = result.getData();
        ratingDao.delete(rating);
        return new SuccessResult(RatingMessages.RatingDeleted);
    }

    @Override
    public DataResult<Rating> getById(int ratingId) {
        var result = ratingDao.findById(ratingId);
        if(result == null){
            return new ErrorDataResult<>(RatingMessages.RatingCannotBeFound);
        }

        return new SuccessDataResult<>(result, RatingMessages.getRatingByIdSuccess);
    }

    @Override
    public DataResult<List<Rating>> getRatings() {
        var result = ratingDao.findAll();
        if(result.isEmpty()){
            return new ErrorDataResult<>(RatingMessages.RatingCannotBeFound);
        }

        return new SuccessDataResult<>(result, RatingMessages.getRatingsSuccess);
    }

    @Override
    public DataResult<List<Rating>> getRatingsByProductId(int productId) {
        var result = ratingDao.findAllByProductId(productId);
        if(result.isEmpty()){
            return new ErrorDataResult<>(RatingMessages.RatingCannotBeFound);
        }

        return new SuccessDataResult<>(result, RatingMessages.getRatingsByProductIdSuccess);
    }

    @Override
    public DataResult<List<Rating>> getRatingsByUserId(int userId) {
        var result = ratingDao.findAllByUserId(userId);
        if(result.isEmpty()){
            return new ErrorDataResult<>(RatingMessages.RatingCannotBeFound);
        }

        return new SuccessDataResult<>(result, RatingMessages.getRatingsByUserIdSuccess);
    }

    @Override
    public DataResult<Float> getAverageRatingByProductId(int productId) {
        var result = getRatingsByProductId(productId);

        if(!result.isSuccess()){
            return new ErrorDataResult<>(RatingMessages.RatingCannotBeFound);
        }

        var ratings = result.getData();
        if(ratings.isEmpty()){
            return new ErrorDataResult<>(RatingMessages.RatingCannotBeFound);
        }

        float totalRating = 0;
        for (Rating rating : ratings) {
            totalRating += rating.getRating();
        }

        float averageRating = totalRating / ratings.size();


        return new SuccessDataResult<>(averageRating, RatingMessages.getAverageRatingByProductIdSuccess);
    }
}

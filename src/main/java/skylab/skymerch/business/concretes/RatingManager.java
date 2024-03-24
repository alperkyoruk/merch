package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.ProductService;
import skylab.skymerch.business.abstracts.RatingService;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.business.constants.ProductMessages;
import skylab.skymerch.business.constants.RatingMessages;
import skylab.skymerch.business.constants.UserMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.RatingDao;
import skylab.skymerch.entities.Dtos.RequestRatingDto;
import skylab.skymerch.entities.Rating;

import java.util.Date;
import java.util.List;

@Service
public class RatingManager implements RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;


    @Override
    public Result addRating(RequestRatingDto requestRatingDto) {
        if(requestRatingDto.getRating() < 1 || requestRatingDto.getRating() > 5){
            return new ErrorResult(RatingMessages.RatingCannotBeNull);
        }

        var userResponse = userService.getUserById(requestRatingDto.getUserId());
        if(!userResponse.isSuccess()){
            return new ErrorResult(UserMessages.UserNotFound);
        }

        var productResponse = productService.getById(requestRatingDto.getProductId());
        if(!productResponse.isSuccess()){
            return new ErrorResult(ProductMessages.ProductCannotBeFound);
        }

        var rating = Rating.builder()
                .rating(requestRatingDto.getRating())
                .submittedAt(new Date())
                .comment(requestRatingDto.getComment())
                .product(productResponse.getData())
                .user(userResponse.getData())
                .build();

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

package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.RatingService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Rating;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private RatingService ratingService;

    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }

    @PostMapping("/addRating")
    public Result addRating(@RequestBody Rating rating){
        return ratingService.addRating(rating);
    }

    @PostMapping("/deleteRating")
    public Result deleteRating(@RequestBody int ratingId){
        return ratingService.deleteRating(ratingId);
    }

    @GetMapping("/getRatingById")
    public DataResult<Rating> getRatingById(@RequestParam int ratingId){
        return ratingService.getById(ratingId);
    }

    @GetMapping("/getRatings")
    public DataResult<List<Rating>> getRatings(){
        return ratingService.getRatings();
    }

    @GetMapping("/getRatingsByProductId")
    public DataResult<List<Rating>> getRatingsByProductId(@RequestParam int productId){
        return ratingService.getRatingsByProductId(productId);
    }

    @GetMapping("/getRatingsByUserId")
    public DataResult<List<Rating>> getRatingsByUserId(@RequestParam int userId){
        return ratingService.getRatingsByUserId(userId);
    }

    @GetMapping("/getAverageRatingByProductId")
    public DataResult<Float> getAverageRatingByProductId(@RequestParam int productId){
        return ratingService.getAverageRatingByProductId(productId);
    }

}

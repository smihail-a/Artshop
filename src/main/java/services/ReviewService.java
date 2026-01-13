package services;

import entity.Reviews;
import org.springframework.stereotype.Service;
import repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Reviews createReview(Reviews review) {
        return reviewRepository.save(review);
    }

    public List<Reviews> getReviewsByArtwork(Long artworkId) {
        return reviewRepository.findByArtworkId(artworkId);
    }

}

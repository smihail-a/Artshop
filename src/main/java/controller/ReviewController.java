package controller;
import entity.Reviews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import services.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Categories", description = "Artwork reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Create review")
    @PostMapping
    public Reviews create(@Valid @RequestBody Reviews review) {
        return reviewService.createReview(review);
    }

    @Operation(summary = "Get reviews by artwork")
    @GetMapping("/artwork/{artworkId}")
    public List<Reviews> byArtwork(@PathVariable Long artworkId) {
        return reviewService.getReviewsByArtwork(artworkId);
    }
}

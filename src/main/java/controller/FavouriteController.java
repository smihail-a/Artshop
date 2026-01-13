package controller;

import entity.Favourites;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import services.FavouriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
@Tag(name = "Categories", description = "Saved Artworks")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @Operation(summary = "Add new artwork to favourites")
    @PostMapping
    public Favourites addToFavourites(@Valid @RequestBody Favourites favourite) {
        return favouriteService.addToFavourites(favourite);
    }

    @Operation(summary = "Get all favourites of one user")
    @GetMapping("/user/{username}")
    public List<Favourites> getUserFavourites(@PathVariable Long username) {
        return favouriteService.getFavouritesByUser(username);
    }
    @Operation(summary = "Remove favourite")
    @DeleteMapping("/{id}")
    public void removeFromFavourites(@PathVariable Long id) {
        favouriteService.removeFavourite(id);
    }
}

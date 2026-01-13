package controller;
import entity.Artwork;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import services.ArtworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/artworks")
@Tag(name = "Artwork", description = "Artwork browsing and management")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @Operation(summary = "Create artwork")
    @PostMapping
    public Artwork createArtwork(@Valid @RequestBody Artwork artwork) {
        return artworkService.createArtwork(artwork);
    }

    @Operation(summary = "Get all artworks")
    @GetMapping
    public List<Artwork> getAllArtworks() {
        return artworkService.getAllArtworks();
    }

    @Operation(summary = "Get artwork by ID")
    @GetMapping("/{id}")
    public Artwork getArtwork(@PathVariable Long id) {
        return artworkService.getArtworkById(id);
    }

    @Operation(summary = "Search artworks by title")
    @GetMapping("/search")
    public List<Artwork> search(@RequestParam String title) {
        return artworkService.searchByTitle(title);
    }

    @Operation(summary = "Get artworks by category")
    @GetMapping("/category/{categoryId}")
    public List<Artwork> byCategory(@PathVariable Long categoryId) {
        return artworkService.getByCategory(categoryId);
    }

    @Operation(summary = "Delete Artwork")
    @DeleteMapping("/{id}")
    public void deleteArtworks(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
    }

}


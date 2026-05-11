package fit.iuh.tourservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.tourservice.dto.TourDto;
import fit.iuh.tourservice.service.TourService;

@RestController
@RequestMapping("/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public List<TourDto> getAll() {
        return tourService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getById(@PathVariable Long id) {
        return tourService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


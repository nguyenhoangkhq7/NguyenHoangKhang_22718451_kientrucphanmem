package fit.iuh.tourservice.dto;

import java.math.BigDecimal;

import fit.iuh.tourservice.model.Tour;

public record TourDto(Long id, String name, BigDecimal price) {

    public static TourDto from(Tour tour) {
        return new TourDto(tour.id(), tour.name(), tour.price());
    }
}


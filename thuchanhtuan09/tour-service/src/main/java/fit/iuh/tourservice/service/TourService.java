package fit.iuh.tourservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fit.iuh.tourservice.dto.TourDto;
import fit.iuh.tourservice.model.Tour;

@Service
public class TourService {

    private final List<Tour> tours = List.of(
            new Tour(1L, "Ha Long Bay", new BigDecimal("1200000")),
            new Tour(2L, "Da Lat", new BigDecimal("900000")),
            new Tour(3L, "Phu Quoc", new BigDecimal("1500000")));

    public List<TourDto> findAll() {
        return tours.stream().map(TourDto::from).toList();
    }

    public Optional<TourDto> findById(Long id) {
        return tours.stream().filter(tour -> tour.id().equals(id)).findFirst().map(TourDto::from);
    }
}


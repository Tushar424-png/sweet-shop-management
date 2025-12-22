package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.entity.Sweet;
import com.incubyte.sweetshop.repository.SweetRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class SweetService {

    private final SweetRepository sweetRepository;

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    public Sweet addSweet(Sweet sweet) {
        return sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    public Sweet updateSweet(Long id, Sweet updatedSweet) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweet.setName(updatedSweet.getName());
        sweet.setCategory(updatedSweet.getCategory());
        sweet.setPrice(updatedSweet.getPrice());
        sweet.setQuantity(updatedSweet.getQuantity());

        return sweetRepository.save(sweet);
    }

    public void deleteSweet(Long id) {
        sweetRepository.deleteById(id);
    }

    public List<Sweet> searchSweets(String name,
                                    String category,
                                    BigDecimal minPrice,
                                    BigDecimal maxPrice) {

        List<Sweet> sweets = sweetRepository.findAll();

        if (name != null) {
            sweets = sweets.stream()
                    .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }

        if (category != null) {
            sweets = sweets.stream()
                    .filter(s -> s.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        if (minPrice != null) {
            sweets = sweets.stream()
                    .filter(s -> s.getPrice().compareTo(minPrice) >= 0)
                    .toList();
        }

        if (maxPrice != null) {
            sweets = sweets.stream()
                    .filter(s -> s.getPrice().compareTo(maxPrice) <= 0)
                    .toList();
        }

        return sweets;
    }
    public Sweet purchaseSweet(Long id) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (sweet.getQuantity() <= 0) {
            throw new RuntimeException("Sweet out of stock");
        }

        sweet.setQuantity(sweet.getQuantity() - 1);
        return sweetRepository.save(sweet);
    }

 
    public Sweet restockSweet(Long id, int amount) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (amount <= 0) {
            throw new RuntimeException("Restock amount must be greater than zero");
        }

        sweet.setQuantity(sweet.getQuantity() + amount);
        return sweetRepository.save(sweet);
    }

}

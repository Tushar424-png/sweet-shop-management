package com.incubyte.sweetshop.controller;

import com.incubyte.sweetshop.entity.Sweet;
import com.incubyte.sweetshop.service.SweetService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

	private final SweetService sweetService;

	public SweetController(SweetService sweetService) {
		this.sweetService = sweetService;
	}

	// ✅ Add Sweet
	@PostMapping
	public Sweet addSweet(@RequestBody Sweet sweet) {
		return sweetService.addSweet(sweet);
	}

	// ✅ Get All Sweets
	@GetMapping
	public List<Sweet> getAllSweets() {
		return sweetService.getAllSweets();
	}

	// ✅ Search (name / category / price range)
	@GetMapping("/search")
	public List<Sweet> searchSweets(@RequestParam(required = false) String name,
			@RequestParam(required = false) String category, @RequestParam(required = false) BigDecimal minPrice,
			@RequestParam(required = false) BigDecimal maxPrice) {
		return sweetService.searchSweets(name, category, minPrice, maxPrice);
	}

	// ✅ Update Sweet
	@PutMapping("/{id}")
	public Sweet updateSweet(@PathVariable Long id, @RequestBody Sweet sweet) {
		return sweetService.updateSweet(id, sweet);
	}

	// ✅ Delete Sweet
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void deleteSweet(@PathVariable Long id) {
	    sweetService.deleteSweet(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/restock")
	public Sweet restockSweet(@PathVariable Long id,
	                          @RequestParam int amount) {
	    return sweetService.restockSweet(id, amount);
	}


	// Purchase API
	@PostMapping("/{id}/purchase")
	public Sweet purchaseSweet(@PathVariable Long id) {
		return sweetService.purchaseSweet(id);
	}

	// Restock API
	
}

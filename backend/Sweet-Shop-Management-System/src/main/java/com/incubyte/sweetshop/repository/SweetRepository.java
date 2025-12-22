package com.incubyte.sweetshop.repository;

import com.incubyte.sweetshop.entity.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetRepository extends JpaRepository<Sweet, Long> {
}

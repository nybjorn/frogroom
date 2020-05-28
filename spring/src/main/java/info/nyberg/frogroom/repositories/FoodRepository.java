package info.nyberg.frogroom.repositories;

import info.nyberg.frogroom.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {}

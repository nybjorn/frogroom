package info.nyberg.frogroom.controllers;

import info.nyberg.frogroom.models.Food;
import info.nyberg.frogroom.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodsController {
  @Autowired private FoodRepository foodRepositry;

  @GetMapping
  public List<Food> list() {
    return foodRepositry.findAll();
  }

  @GetMapping
  @RequestMapping("{id}")
  public Food get(@PathVariable Long id) {
    return foodRepositry.getOne(id);
  }
}

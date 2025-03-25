package calcapp.dish;

import calcapp.dish.dto.NewDishDto;
import calcapp.dish.dto.DishDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/dishes")
@Slf4j
public class DishController {

	private final DishService dishService;

	@Autowired
	public DishController(DishService dishService) {
		this.dishService = dishService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DishDto create(@Valid @RequestBody NewDishDto dto) {
		DishDto createdDish = dishService.create(dto);
		log.info("Created dish {}", createdDish.toString());
		return createdDish;
	}

	@DeleteMapping("/{dishId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDish(@PathVariable("dishId") Long dishId) {
		dishService.delete(dishId);
		log.info("Deleted dish, id = " + dishId);
	}

	@GetMapping
	public List<DishDto> getDishes() {
		return dishService.getDishes();
	}
}

package calcapp.meal;

import calcapp.meal.dto.NewMealDto;
import calcapp.meal.dto.MealDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meals")
@Slf4j
public class MealController {

	private final MealService mealService;

	@Autowired
	public MealController(MealService mealService) {
		this.mealService = mealService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MealDto create(@Valid @RequestBody NewMealDto dto) {
		MealDto createdMeal = mealService.createMeal(dto);
		log.info("Created meal {}", createdMeal.toString());
		return createdMeal;
	}

	@GetMapping("/user/{userId}")
	public List<MealDto> getMealsByUser(@PathVariable("userId") Long userId) {
		return mealService.getMealsByUser(userId);
	}



}

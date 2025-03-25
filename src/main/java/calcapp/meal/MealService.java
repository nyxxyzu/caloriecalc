package calcapp.meal;

import calcapp.meal.dto.MealDto;
import calcapp.meal.dto.NewMealDto;


import java.util.List;

public interface MealService {
	MealDto createMeal(NewMealDto dto);

	List<MealDto> getMealsByUser(Long userId);

}

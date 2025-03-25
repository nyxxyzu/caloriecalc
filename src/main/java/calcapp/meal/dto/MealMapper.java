package calcapp.meal.dto;

import calcapp.dish.dto.DishMapper;
import calcapp.meal.Meal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MealMapper {


	public static MealDto toMealDto(Meal meal) {
		MealDto dto = new MealDto();
		dto.setMealTime(meal.getMealTime());
		dto.setId(meal.getId());
		dto.setDishes(meal.getDishes() != null ? meal.getDishes().stream()
				.map(DishMapper::toDishDto).toList() : null);
		dto.setCalories(meal.getCalories());
		return dto;
	}
}

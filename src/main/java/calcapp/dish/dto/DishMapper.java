package calcapp.dish.dto;

import calcapp.dish.Dish;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishMapper {

	public static Dish toDish(NewDishDto dto) {
		Dish dish = new Dish();
		dish.setName(dto.getName());
		dish.setCalories(dto.getCalories());
		dish.setFat(dto.getFat());
		dish.setProtein(dto.getProtein());
		dish.setCarbs(dto.getCarbs());
		return dish;
	}

	public static DishDto toDishDto(Dish dish) {
		DishDto dto = new DishDto();
		dto.setId(dish.getId());
		dto.setCalories(dish.getCalories());
		dto.setCarbs(dish.getCarbs());
		dto.setFat(dish.getFat());
		dto.setProtein(dish.getProtein());
		dto.setName(dish.getName());
		return dto;
	}
}

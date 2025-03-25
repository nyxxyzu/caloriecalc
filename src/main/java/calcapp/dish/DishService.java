package calcapp.dish;

import calcapp.dish.dto.DishDto;
import calcapp.dish.dto.NewDishDto;

import java.util.List;

public interface DishService {
	DishDto create(NewDishDto dto);

	void delete(Long dishId);

	List<DishDto> getDishes();
}

package calcapp.meal.dto;

import calcapp.dish.dto.DishDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MealDto {

	private Long id;
	private List<DishDto> dishes;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime mealTime;
	private Double calories;

}

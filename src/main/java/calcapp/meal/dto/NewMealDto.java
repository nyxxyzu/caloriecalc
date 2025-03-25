package calcapp.meal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMealDto {

	@NotNull
	private List<Long> dishes;
	@NotNull
	private Long userId;

}

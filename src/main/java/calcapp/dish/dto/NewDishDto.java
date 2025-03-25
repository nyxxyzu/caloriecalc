package calcapp.dish.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDishDto {

	@NotBlank
	@Size(min = 2, max = 250)
	private String name;
	@NotNull
	@PositiveOrZero
	private Double calories;
	@PositiveOrZero
	private Integer protein;
	@PositiveOrZero
	private Integer fat;
	@PositiveOrZero
	private Integer carbs;
}

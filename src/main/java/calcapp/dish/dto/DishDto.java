package calcapp.dish.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

	private Long id;
	private String name;
	private Double calories;
	private Integer protein;
	private Integer fat;
	private Integer carbs;
}

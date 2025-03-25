package calcapp.user.dto;

import calcapp.user.Goal;
import lombok.Data;

@Data
public class UserDto {

	private Long id;
	private String name;
	private String email;
	private Integer age;
	private Integer weight;
	private Integer height;
	private Goal goal;
	private Double dailyCalories;
}

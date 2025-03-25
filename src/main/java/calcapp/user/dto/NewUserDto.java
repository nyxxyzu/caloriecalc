package calcapp.user.dto;

import calcapp.user.Goal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

	@NotBlank
	@Size(min = 2, max = 250)
	private String name;
	@NotBlank
	@Email
	@Size(min = 6, max = 256)
	private String email;
	@NotNull
	@PositiveOrZero
	private Integer age;
	@Positive
	@Min(10)
	@Max(300)
	@NotNull
	private Integer weight;
	@Min(10)
	@Max(300)
	@NotNull
	@Positive
	private Integer height;
	@NotNull
	private Goal goal;
}

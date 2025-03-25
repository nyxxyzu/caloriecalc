package calcapp.report.dto;

import calcapp.meal.dto.MealDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportDto {

	private List<MealDto> meals;
	private Double calories;
	private LocalDate date;

}

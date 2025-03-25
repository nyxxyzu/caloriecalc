package calcapp;

import calcapp.dish.Dish;
import calcapp.meal.Meal;
import calcapp.meal.MealRepository;
import calcapp.meal.dto.MealMapper;
import calcapp.report.ReportService;
import calcapp.report.dto.CalorieReportDto;
import calcapp.report.dto.DailyReportDto;
import calcapp.user.Goal;
import calcapp.user.User;
import calcapp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReportServiceImplTest {

	@MockitoBean
	private final MealRepository mockMealRepository;

	@MockitoBean
	private final UserRepository mockUserRepository;

	private final ReportService reportService;

	private Meal savedMeal;
	private User user;
	private Dish dish1;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		dish1 = new Dish(1L, "name1", 150.0, 5, 5, 5);
		user = new User(1L, "username", "email@email.com", 20, 120, 150, Goal.GAIN, 1500.0);
		savedMeal = new Meal(1L, List.of(dish1), LocalDateTime.of(2025, 12, 12, 8, 8, 8), user, 300.0);
	}

	@Test
	void testDailyReport() {
		when(mockMealRepository.findByUserIdAndDate(Mockito.anyLong(), Mockito.any())).thenReturn(List.of(savedMeal));
		when(mockUserRepository.existsById(Mockito.anyLong())).thenReturn(true);
		DailyReportDto dto = reportService.getDailyReport(1L);
		assertThat(dto.getCalories(), equalTo(300.0));
		assertThat(dto.getDate(), equalTo(LocalDate.now()));
		assertThat(dto.getMeals(), equalTo(Stream.of(savedMeal)
				.map(MealMapper::toMealDto)
				.toList()));
		Mockito.verify(mockUserRepository, times(1)).existsById(1L);
		Mockito.verify(mockMealRepository, times(1)).findByUserIdAndDate(Mockito.anyLong(), Mockito.any());

	}

	@Test
	void testGetCaloryReport() {
		when(mockUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user));
		when(mockMealRepository.findByUserIdAndDate(Mockito.anyLong(), Mockito.any())).thenReturn(List.of(savedMeal));
		CalorieReportDto dto = reportService.getCalorieReport(1L);
		assertThat(dto.getMessage(), equalTo("До дневной нормы калорий Вам не хватает " + (user.getDailyCalories() - savedMeal.getCalories()) + " калорий."));
		Mockito.verify(mockUserRepository, times(1)).findById(1L);
		Mockito.verify(mockMealRepository, times(1)).findByUserIdAndDate(Mockito.anyLong(), Mockito.any());
	}

	@Test
	void testGetHistory() {
		when(mockMealRepository.findByUserId(Mockito.anyLong())).thenReturn(List.of(savedMeal));
		when(mockUserRepository.existsById(Mockito.anyLong())).thenReturn(true);
		List<DailyReportDto> dtoList = reportService.getHistory(1L);
		assertThat(dtoList.getFirst().getCalories(), equalTo(300.0));
		assertThat(dtoList.getFirst().getDate(), equalTo(savedMeal.getMealTime().toLocalDate()));
		assertThat(dtoList.getFirst().getMeals(), equalTo(Stream.of(savedMeal)
				.map(MealMapper::toMealDto)
				.toList()));
		Mockito.verify(mockUserRepository, times(1)).existsById(1L);
		Mockito.verify(mockMealRepository, times(1)).findByUserId(Mockito.anyLong());
	}
}

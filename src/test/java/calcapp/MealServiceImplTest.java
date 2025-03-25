package calcapp;

import calcapp.dish.Dish;
import calcapp.dish.DishRepository;
import calcapp.dish.dto.DishMapper;
import calcapp.meal.Meal;
import calcapp.meal.MealRepository;
import calcapp.meal.MealService;
import calcapp.meal.dto.MealDto;
import calcapp.meal.dto.MealMapper;
import calcapp.meal.dto.NewMealDto;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MealServiceImplTest {

	@MockitoBean
	private final MealRepository mockMealRepository;

	@MockitoBean
	private final UserRepository mockUserRepository;

	@MockitoBean
	private final DishRepository mockDishRepository;

	private final MealService mealService;

	private NewMealDto newMealDto;
	private Meal savedMeal;
	private Dish dish1;
	private Dish dish2;
	private Dish dish3;
	private User user;
	private List<Dish> dishes;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		dish1 = new Dish(1L, "name1", 150.0, 5, 5, 5);
		dish2 = new Dish(2L, "name2", 150.0, 5, 5, 5);
		dish3 = new Dish(3L, "name3", 150.0, 5, 5, 5);
		dishes = List.of(dish1, dish2, dish3);
		user = new User(1L, "username", "email@email.com", 20, 120, 150, Goal.GAIN, 1500.0);
		newMealDto = new NewMealDto(List.of(1L,2L,3L), 1L);
		savedMeal = new Meal(1L, dishes, LocalDateTime.of(2025, 12, 12, 8, 8, 8), user, 600.0);
	}

	@Test
	void testCreateMeal() {
		when(mockMealRepository.save(Mockito.any(Meal.class))).thenReturn(savedMeal);
		when(mockUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user));
		when(mockDishRepository.findByIdIn(Mockito.anyList())).thenReturn(dishes);
		MealDto dto = mealService.createMeal(newMealDto);
		assertThat(dto.getId(), equalTo(1L));
		assertThat(dto.getMealTime(), equalTo(LocalDateTime.of(2025, 12, 12, 8, 8, 8)));
		assertThat(dto.getCalories(), equalTo(600.0));
		assertThat(dto.getDishes(), equalTo(dishes.stream()
				.map(DishMapper::toDishDto).toList()));
		Mockito.verify(mockUserRepository, times(1)).findById(Mockito.anyLong());
		Mockito.verify(mockDishRepository, times(1)).findByIdIn(Mockito.anyList());
		Mockito.verify(mockMealRepository, times(1)).save(Mockito.any(Meal.class));

	}

	@Test
	void testGetMealsByUser() {
		when(mockUserRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(mockMealRepository.findByUserId(Mockito.anyLong())).thenReturn(List.of(savedMeal));
		List<MealDto> meals = mealService.getMealsByUser(1L);
		assertThat(meals.getFirst(), equalTo(MealMapper.toMealDto(savedMeal)));
		Mockito.verify(mockUserRepository, times(1)).existsById(1L);
		Mockito.verify(mockMealRepository, times(1)).findByUserId(1L);

	}
}

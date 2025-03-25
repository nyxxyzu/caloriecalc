package calcapp.meal;

import calcapp.dish.Dish;
import calcapp.dish.DishRepository;
import calcapp.exceptions.NotFoundException;
import calcapp.meal.dto.MealDto;
import calcapp.meal.dto.MealMapper;
import calcapp.meal.dto.NewMealDto;
import calcapp.user.User;
import calcapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class MealServiceImpl implements MealService {

	private final MealRepository mealRepository;
	private final DishRepository dishRepository;
	private final UserRepository userRepository;

	@Autowired
	public MealServiceImpl(MealRepository mealRepository, DishRepository dishRepository,
	UserRepository userRepository) {
		this.mealRepository = mealRepository;
		this.dishRepository = dishRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public MealDto createMeal(NewMealDto dto) {
		Meal meal = new Meal();
		double calories = 0;
		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new NotFoundException("User not found"));
		List<Dish> dishes = dishRepository.findByIdIn(dto.getDishes());
		for (Dish dish : dishes) {
			calories = calories + dish.getCalories();
		}
		meal.setUser(user);
		meal.setDishes(dishes);
		meal.setMealTime(LocalDateTime.now());
		meal.setCalories(calories);
		return MealMapper.toMealDto(mealRepository.save(meal));
	}

	@Override
	public List<MealDto> getMealsByUser(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		List<Meal> meals = mealRepository.findByUserId(userId);
		return meals.stream()
				.map(MealMapper::toMealDto)
				.toList();

	}


}

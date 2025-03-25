package calcapp.report;

import calcapp.exceptions.NotFoundException;
import calcapp.meal.Meal;
import calcapp.meal.MealRepository;
import calcapp.meal.dto.MealMapper;
import calcapp.report.dto.CalorieReportDto;
import calcapp.report.dto.DailyReportDto;
import calcapp.user.User;
import calcapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

	private final MealRepository mealRepository;
	private final UserRepository userRepository;

	@Autowired
	public ReportServiceImpl(MealRepository mealRepository, UserRepository userRepository) {
		this.mealRepository = mealRepository;
		this.userRepository = userRepository;
	}

	@Override
	public DailyReportDto getDailyReport(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		DailyReportDto dto = new DailyReportDto();
		double calories = 0;
		List<Meal> meals = mealRepository.findByUserIdAndDate(userId, LocalDate.now());
		for (Meal meal : meals) {
			calories = calories + meal.getCalories();
		}
		dto.setMeals(meals.stream()
				.map(MealMapper::toMealDto)
				.toList());
		dto.setCalories(calories);
		dto.setDate(LocalDate.now());
		return dto;
	}

	@Override
	public CalorieReportDto getCalorieReport(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		double calories = 0;
		List<Meal> meals = mealRepository.findByUserIdAndDate(userId, LocalDate.now());
		for (Meal meal : meals) {
			calories = calories + meal.getCalories();
		}
		if (user.getDailyCalories() > calories) {
			return new CalorieReportDto("До дневной нормы калорий Вам не хватает " + (user.getDailyCalories() - calories) + " калорий.");
		} else if (user.getDailyCalories() == calories) {
			return new CalorieReportDto("Вы уложились в дневную норму калорий!");
		} else {
			return new CalorieReportDto("Вы превысили дневную норму калорий на " + (calories - user.getDailyCalories()) + " калорий");
		}
	}

	@Override
	public List<DailyReportDto> getHistory(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User not found");
		}
		List<Meal> meals = mealRepository.findByUserId(userId);
		Map<LocalDate, List<Meal>> mealsByDay = meals.stream()
				.collect(Collectors.groupingBy(meal -> meal.getMealTime().toLocalDate()));

		return mealsByDay.entrySet().stream()
				.map(entry -> new DailyReportDto(
						entry.getValue().stream()
								.map(MealMapper::toMealDto)
								.toList(),
						entry.getValue().stream()
								.mapToDouble(Meal::getCalories)
								.sum(),
						entry.getKey()
				)).toList();
	}
}

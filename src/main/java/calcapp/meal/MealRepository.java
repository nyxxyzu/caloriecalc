package calcapp.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

	List<Meal> findByUserId(Long userId);

	@Query(value = "SELECT m FROM Meal m " +
			"WHERE m.user.id = ?1 AND FUNCTION('DATE', m.mealTime) = ?2")
	List<Meal> findByUserIdAndDate(Long userId, LocalDate date);
}

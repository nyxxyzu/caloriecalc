package calcapp.dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

	List<Dish> findByIdIn(List<Long> dishIds);
}

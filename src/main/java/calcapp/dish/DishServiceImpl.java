package calcapp.dish;

import calcapp.dish.dto.DishDto;
import calcapp.dish.dto.DishMapper;
import calcapp.dish.dto.NewDishDto;
import calcapp.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class DishServiceImpl implements DishService {

	private final DishRepository dishRepository;

	@Autowired
	public DishServiceImpl(DishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}

	@Override
	@Transactional
	public DishDto create(NewDishDto dto) {
		Dish dish = DishMapper.toDish(dto);
		return DishMapper.toDishDto(dishRepository.save(dish));
	}

	@Override
	@Transactional
	public void delete(Long dishId) {
		if (!dishRepository.existsById(dishId)) {
			throw new NotFoundException("Dish " + dishId + " was not found");
		}
		dishRepository.deleteById(dishId);
	}

	@Override
	public List<DishDto> getDishes() {
		return dishRepository.findAll()
				.stream()
				.map(DishMapper::toDishDto)
				.toList();
	}
}

package calcapp;

import calcapp.dish.Dish;
import calcapp.dish.DishRepository;
import calcapp.dish.DishService;
import calcapp.dish.dto.DishDto;
import calcapp.dish.dto.NewDishDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DishServiceImplTest {


	@MockitoBean
	private final DishRepository mockDishRepository;


	private final DishService dishService;

	private NewDishDto newDishDto;
	private Dish savedDish;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		newDishDto = new NewDishDto("name", 150.0, 5, 5, 5);
		savedDish = new Dish(1L, "name", 150.0, 5, 5, 5);
	}

	@Test
	void testCreate() {
		when(mockDishRepository.save(Mockito.any(Dish.class))).thenReturn(savedDish);
		DishDto testDto = dishService.create(newDishDto);
		assertThat(testDto.getId(), equalTo(1L));
		assertThat(testDto.getName(), equalTo("name"));
		assertThat(testDto.getCalories(), equalTo(150.0));
		assertThat(testDto.getFat(), equalTo(5));
		assertThat(testDto.getCarbs(), equalTo(5));
		assertThat(testDto.getProtein(),equalTo(5));
		Mockito.verify(mockDishRepository, times(1)).save(Mockito.any(Dish.class));
	}

	@Test
	void testDelete() {
		when(mockDishRepository.existsById(Mockito.anyLong())).thenReturn(true);
		dishService.delete(1L);
		Mockito.verify(mockDishRepository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testGet() {
		List<Dish> dishes = new ArrayList<>();
		dishes.add(savedDish);
		when(mockDishRepository.findAll()).thenReturn(dishes);
		List<DishDto> savedDishes = dishService.getDishes();
		assertThat(dishes.size(), equalTo(savedDishes.size()));
		assertThat(savedDishes.getFirst().getId(), equalTo(savedDish.getId()));
		Mockito.verify(mockDishRepository, times(1)).findAll();
	}




}

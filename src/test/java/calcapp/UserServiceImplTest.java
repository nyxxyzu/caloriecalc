package calcapp;

import calcapp.user.dto.UserDto;
import calcapp.user.Goal;
import calcapp.user.User;
import calcapp.user.UserRepository;
import calcapp.user.UserService;
import calcapp.user.dto.NewUserDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImplTest {

	@MockitoBean
	private final UserRepository mockUserRepository;


	private final UserService userService;

	private NewUserDto newUserDto;
	private User savedUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		newUserDto = new NewUserDto("username", "email@email.com", 20, 120, 150, Goal.GAIN);
		savedUser = new User(1L, "username", "email@email.com", 20, 120, 150, Goal.GAIN, 1500.0);
	}

	@Test
	void testCreate() {
		when(mockUserRepository.save(Mockito.any(User.class))).thenReturn(savedUser);
		UserDto testDto = userService.create(newUserDto);
		assertThat(testDto.getId(), equalTo(1L));
		assertThat(testDto.getName(), equalTo("username"));
		assertThat(testDto.getDailyCalories(), equalTo(1500.0));
		assertThat(testDto.getAge(), equalTo(20));
		assertThat(testDto.getWeight(), equalTo(120));
		assertThat(testDto.getHeight(),equalTo(150));
		Mockito.verify(mockUserRepository, times(1)).save(Mockito.any(User.class));
	}

	@Test
	void testDelete() {
		when(mockUserRepository.existsById(Mockito.anyLong())).thenReturn(true);
		userService.delete(1L);
		Mockito.verify(mockUserRepository, times(1)).deleteById(Mockito.anyLong());
	}

	@Test
	void testGetUserById() {
		when(mockUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(savedUser));
		UserDto dto = userService.getUserById(1L);
		assertThat(dto.getId(), equalTo(savedUser.getId()));
		assertThat(dto.getName(), equalTo(savedUser.getName()));
		assertThat(dto.getDailyCalories(), equalTo(savedUser.getDailyCalories()));
		assertThat(dto.getAge(), equalTo(savedUser.getAge()));
		assertThat(dto.getWeight(), equalTo(savedUser.getWeight()));
		assertThat(dto.getHeight(),equalTo(savedUser.getHeight()));
		Mockito.verify(mockUserRepository, times(1)).findById(1L);
	}
}

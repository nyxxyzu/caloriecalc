package calcapp.user.dto;

import calcapp.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

	public static User toUser(NewUserDto dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setAge(dto.getAge());
		user.setHeight(dto.getHeight());
		user.setWeight(dto.getWeight());
		user.setGoal(dto.getGoal());
		return user;
	}

	public static UserDto toUserDto(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setAge(user.getAge());
		dto.setName(user.getName());
		dto.setGoal(user.getGoal());
		dto.setEmail(user.getEmail());
		dto.setHeight(user.getHeight());
		dto.setWeight(user.getWeight());
		dto.setDailyCalories(user.getDailyCalories());
		return dto;
	}
}

package calcapp.user;

import calcapp.user.dto.NewUserDto;
import calcapp.user.dto.UserDto;

public interface UserService {
	UserDto create(NewUserDto dto);

	void delete(Long userId);

	UserDto getUserById(Long userId);
}

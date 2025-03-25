package calcapp.user;

import calcapp.exceptions.NotFoundException;
import calcapp.user.dto.NewUserDto;
import calcapp.user.dto.UserDto;
import calcapp.user.dto.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDto create(NewUserDto dto) {
		User user = UserMapper.toUser(dto);
		user.setDailyCalories((10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) + 5);
		return UserMapper.toUserDto(userRepository.save(user));
	}

	@Override
	@Transactional
	public void delete(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new NotFoundException("User " + userId + " was not found");
		}
		userRepository.deleteById(userId);
	}

	@Override
	public UserDto getUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return UserMapper.toUserDto(user);
	}
}

package br.com.sulamerica.api.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sulamerica.api.entity.User;
import br.com.sulamerica.api.entity.dto.UserSearchDTO;
import br.com.sulamerica.api.repository.UserRepository;
import br.com.sulamerica.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createOrUpdate(User user) {
		logger.info("[UserService][createOrUpdate][user: {}]", user.toString());
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findById(Long id) {
		logger.info("[UserService][findById][id: {}]", id);
		return userRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		logger.info("[UserService][delete][id: {}]", id);
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findByCriterias(UserSearchDTO userSearchDTO) {
		logger.info("[UserService][findByCriterias][userSearchDTO: {}]", userSearchDTO.toString());
		if (userSearchDTO.getActive() != null) {
			return userRepository
					.findByNameIgnoreCaseContainingAndCpfContainingAndProfileNameIgnoreCaseContainingAndRoleNameIgnoreCaseContainingAndActive(
							userSearchDTO.getName(), userSearchDTO.getCpf(), userSearchDTO.getProfile(),
							userSearchDTO.getRole(), userSearchDTO.getActive());
		} else {
			return userRepository
					.findByNameIgnoreCaseContainingAndCpfContainingAndProfileNameIgnoreCaseContainingAndRoleNameIgnoreCaseContaining(
							userSearchDTO.getName(), userSearchDTO.getCpf(), userSearchDTO.getProfile(),
							userSearchDTO.getRole());
		}
	}

	@Override
	public User findByCpf(String cpf) {
		logger.info("[UserService][findById][cpf: {}]", cpf);
		return userRepository.findByCpf(cpf);
	}

	@Override
	public List<User> findByGender(String gender) {
		logger.info("[UserService][findByGender][gender: {}]", gender);
		return userRepository.findByGender(gender);
	}

	@Override
	public List<User> findByGenderFemaleAndGreatherThan18() {
		logger.info("[UserService][findByGenderFemaleAndGreatherThan18]");
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.YEAR, -18);
		
		return userRepository.findByGenderAndBirthDateLessThan("F", calendar.getTime());
	}

	@Override
	public List<User> findByCPFStart0() {
		logger.info("[UserService][findByCPFStart0]");
		return userRepository.findByCpfLike("0%");
	}

}

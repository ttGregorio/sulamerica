package br.com.sulamerica.api.service;

import java.util.List;
import java.util.Optional;

import br.com.sulamerica.api.entity.User;
import br.com.sulamerica.api.entity.dto.UserSearchDTO;

public interface UserService {

	User createOrUpdate(User user);

	Optional<User> findById(Long id);

	void delete(Long id);

	List<User> findByCriterias(UserSearchDTO userSearchDTO);

	User findByCpf(String email);

	List<User> findByGender(String gender);

	List<User> findByGenderFemaleAndGreatherThan18();

	List<User> findByCPFStart0();

}
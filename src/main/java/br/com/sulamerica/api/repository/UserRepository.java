package br.com.sulamerica.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.sulamerica.api.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByNameIgnoreCaseContainingAndCpfContainingAndProfileNameIgnoreCaseContainingAndRoleNameIgnoreCaseContaining(String name, String cpf,
			String profile, String role);

	List<User> findByNameIgnoreCaseContainingAndCpfContainingAndProfileNameIgnoreCaseContainingAndRoleNameIgnoreCaseContainingAndActive(String name,
			String cpf, String profile, String role, Boolean active);

	User findByCpf(String cpf);

	List<User> findByGender(String gender);

	List<User> findByGenderAndBirthDateLessThan(String gender, Date time);

	List<User> findByCpfLike(String cpf);

}

package br.com.sulamerica.api.service;

import java.util.Optional;

import br.com.sulamerica.api.entity.Role;

public interface RoleService {

	Role createOrUpdate(Role role);

	Optional<Role> findById(Long id);

	void delete(Long id);

}
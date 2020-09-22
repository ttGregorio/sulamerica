package br.com.sulamerica.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.sulamerica.api.entity.Role;

public interface RoleRepository  extends CrudRepository<Role, Long> {

}

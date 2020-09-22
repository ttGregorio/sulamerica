package br.com.sulamerica.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.sulamerica.api.entity.Profile;

public interface ProfileRepository  extends CrudRepository<Profile, Long> {

}

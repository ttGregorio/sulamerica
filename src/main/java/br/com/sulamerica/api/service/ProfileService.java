package br.com.sulamerica.api.service;

import java.util.Optional;

import br.com.sulamerica.api.entity.Profile;

public interface ProfileService {

	Profile createOrUpdate(Profile profile);

	Optional<Profile> findById(Long id);

	void delete(Long id);

}
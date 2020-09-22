package br.com.sulamerica.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sulamerica.api.entity.Profile;
import br.com.sulamerica.api.repository.ProfileRepository;
import br.com.sulamerica.api.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile createOrUpdate(Profile profile) {
		logger.info("[ProfileService][createOrUpdate][profile: {}]", profile.toString());
		return profileRepository.save(profile);
	}

	@Override
	public Optional<Profile> findById(Long id) {
		logger.info("[ProfileService][findById][id: {}]", id);
		return profileRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		logger.info("[ProfileService][delete][id: {}]", id);
		profileRepository.deleteById(id);
	}

}

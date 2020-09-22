package br.com.sulamerica.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sulamerica.api.entity.Role;
import br.com.sulamerica.api.repository.RoleRepository;
import br.com.sulamerica.api.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleRepository userRepository;

	@Override
	public Role createOrUpdate(Role user) {
		logger.info("[RoleService][createOrUpdate][user: {}]", user.toString());
		return userRepository.save(user);
	}

	@Override
	public Optional<Role> findById(Long id) {
		logger.info("[RoleService][findById][id: {}]", id);
		return userRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		logger.info("[RoleService][delete][id: {}]", id);
		userRepository.deleteById(id);
	}

}

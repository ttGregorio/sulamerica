package br.com.sulamerica.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sulamerica.api.entity.Response;
import br.com.sulamerica.api.entity.Profile;
import br.com.sulamerica.api.service.ProfileService;

@RestController
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping(path = "profiles")
	public ResponseEntity<Response<Profile>> create(HttpServletRequest request, @RequestBody Profile profile,
			BindingResult result) {
		Response<Profile> response = new Response<>();
		try {
			validateCreateProfile(profile, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

			Profile profilePersisted = (Profile) profileService.createOrUpdate(profile);
			response.setData(profilePersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping(path = "profiles")
	public ResponseEntity<Response<Profile>> update(HttpServletRequest request, @RequestBody Profile profile,
			BindingResult result) {
		Response<Profile> response = new Response<>();
		try {
			validateUpdateProfile(profile, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

			Profile profilePersisted = (Profile) profileService.createOrUpdate(profile);
			response.setData(profilePersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "profiles/{id}")
	public ResponseEntity<Response<Profile>> findById(@PathVariable Long id) {
		Response<Profile> response = new Response<>();
		Optional<Profile> profile = profileService.findById(id);

		if (profile.isPresent()) {
			response.setData(profile.get());
		} else {
			response.getErrors().add("Register not found for id ".concat(id.toString()));
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "profiles/{id}")
	@PreAuthorize("hasAnyProfile('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
		Response<String> response = new Response<>();
		Optional<Profile> profile = profileService.findById(id);

		if (profile.isPresent()) {
			profileService.delete(id);
			response.setData("Profile ".concat(id.toString()).concat(" removed."));
		} else {
			response.getErrors().add("Register not found for id ".concat(id.toString()));
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	private void validateCreateProfile(Profile profile, BindingResult result) {
		if (profile.getName() == null) {
			result.addError(new ObjectError("Profile", "name not found"));
		}
	
	}

	private void validateUpdateProfile(Profile profile, BindingResult result) {
		if (profile.getName() == null) {
			result.addError(new ObjectError("Profile", "name not found"));
		}
		
		if (profile.getId() == null) {
			result.addError(new ObjectError("Profile", "Id not found"));
		}
	}
}

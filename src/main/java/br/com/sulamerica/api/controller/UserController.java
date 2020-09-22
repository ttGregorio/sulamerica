package br.com.sulamerica.api.controller;

import java.util.List;
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
import br.com.sulamerica.api.entity.User;
import br.com.sulamerica.api.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	//@Autowired
	//private PasswordEncoder passwordEncoder;

	@PostMapping(path = "/users")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		Response<User> response = new Response<>();
		try {
			validateCreateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

	//		user.setPassword(passwordEncoder.encode(user.getPassword()));

			User userPersisted = (User) userService.createOrUpdate(user);
			response.setData(userPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping(path = "/users")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> update(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		Response<User> response = new Response<>();
		try {
			validateUpdateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

	//		user.setPassword(passwordEncoder.encode(user.getPassword()));

			User userPersisted = (User) userService.createOrUpdate(user);
			response.setData(userPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "users/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> findById(@PathVariable Long id) {
		Response<User> response = new Response<>();
		Optional<User> user = userService.findById(id);

		if (user.isPresent()) {
			response.setData(user.get());
		} else {
			response.getErrors().add("Register not found for id ".concat(id.toString()));
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "users/{gender}/gender")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<User>>> findByGender(@PathVariable String gender) {
		Response<List<User>> response = new Response<>();
		response.setData(userService.findByGender(gender));


		return ResponseEntity.ok(response);
	}

	
	@GetMapping(value = "users/female-plus-18")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<User>>> findByGenderFemaleGreather18() {
		Response<List<User>> response = new Response<>();
		response.setData(userService.findByGenderFemaleAndGreatherThan18());


		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "users/cpf-start-0")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<User>>> findByCPFZero() {
		Response<List<User>> response = new Response<>();
		response.setData(userService.findByCPFStart0());


		return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping(value = "users/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
		Response<String> response = new Response<>();
		Optional<User> user = userService.findById(id);

		if (user.isPresent()) {
			userService.delete(id);
			response.setData("User ".concat(id.toString()).concat(" removed."));
		} else {
			response.getErrors().add("Register not found for id ".concat(id.toString()));
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	private void validateCreateUser(User user, BindingResult result) {
		if (user.getName() == null) {
			result.addError(new ObjectError("User", "name not found"));
		}
		if (user.getBirthDate() == null) {
			result.addError(new ObjectError("User", "birthdate not found"));
		}
		if (user.getCpf() == null) {
			result.addError(new ObjectError("User", "cpf not found"));
		}
		if (user.getGender() == null) {
			result.addError(new ObjectError("User", "gender not found"));
		}
		if (user.getPassword() == null) {
			result.addError(new ObjectError("User", "password not found"));
		}
		if (user.getProfile() == null) {
			result.addError(new ObjectError("User", "profile not found"));
		}
		if (user.getRole() == null) {
			result.addError(new ObjectError("User", "role not found"));
		}

	}

	private void validateUpdateUser(User user, BindingResult result) {
		if (user.getName() == null) {
			result.addError(new ObjectError("User", "name not found"));
		}
		if (user.getBirthDate() == null) {
			result.addError(new ObjectError("User", "birthdate not found"));
		}
		if (user.getCpf() == null) {
			result.addError(new ObjectError("User", "cpf not found"));
		}
		if (user.getGender() == null) {
			result.addError(new ObjectError("User", "gender not found"));
		}
		if (user.getPassword() == null) {
			result.addError(new ObjectError("User", "password not found"));
		}
		if (user.getProfile() == null) {
			result.addError(new ObjectError("User", "profile not found"));
		}
		if (user.getRole() == null) {
			result.addError(new ObjectError("User", "role not found"));
		}
		if (user.getId() == null) {
			result.addError(new ObjectError("User", "Id not found"));
		}
	}
}

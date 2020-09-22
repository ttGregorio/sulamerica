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
import br.com.sulamerica.api.entity.Role;
import br.com.sulamerica.api.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping(path = "roles")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Role>> create(HttpServletRequest request, @RequestBody Role role,
			BindingResult result) {
		Response<Role> response = new Response<>();
		try {
			validateCreateRole(role, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

			Role rolePersisted = (Role) roleService.createOrUpdate(role);
			response.setData(rolePersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping(path = "roles")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Role>> update(HttpServletRequest request, @RequestBody Role role,
			BindingResult result) {
		Response<Role> response = new Response<>();
		try {
			validateUpdateRole(role, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

			Role rolePersisted = (Role) roleService.createOrUpdate(role);
			response.setData(rolePersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "roles/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Role>> findById(@PathVariable Long id) {
		Response<Role> response = new Response<>();
		Optional<Role> role = roleService.findById(id);

		if (role.isPresent()) {
			response.setData(role.get());
		} else {
			response.getErrors().add("Register not found for id ".concat(id.toString()));
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "roles/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
		Response<String> response = new Response<>();
		Optional<Role> role = roleService.findById(id);

		if (role.isPresent()) {
			roleService.delete(id);
			response.setData("Role ".concat(id.toString()).concat(" removed."));
		} else {
			response.getErrors().add("Register not found for id ".concat(id.toString()));
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	private void validateCreateRole(Role role, BindingResult result) {
		if (role.getName() == null) {
			result.addError(new ObjectError("Role", "name not found"));
		}
	
	}

	private void validateUpdateRole(Role role, BindingResult result) {
		if (role.getName() == null) {
			result.addError(new ObjectError("Role", "name not found"));
		}
		
		if (role.getId() == null) {
			result.addError(new ObjectError("Role", "Id not found"));
		}
	}
}

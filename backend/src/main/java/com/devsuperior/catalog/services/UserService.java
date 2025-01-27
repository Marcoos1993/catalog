package com.devsuperior.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.catalog.dto.RoleDTO;
import com.devsuperior.catalog.dto.UserDTO;
import com.devsuperior.catalog.entities.Role;
import com.devsuperior.catalog.entities.User;
import com.devsuperior.catalog.repositories.RoleRepository;
import com.devsuperior.catalog.repositories.UserRepository;
import com.devsuperior.catalog.services.exceptions.DatabaseException;
import com.devsuperior.catalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> product = userRepository.findById(id);
		User entity = product.orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));

		return new UserDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = userRepository.findAll(pageable);

		return list.map(x -> new UserDTO(x));
	}
	
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entity = userRepository.getReferenceById(id);
			copyToEntity(entity, dto);	
			entity = userRepository.save(entity);
			
			return new UserDTO(entity);
		}
		catch(EntityNotFoundException e) {
					throw new ResourceNotFoundException("Id number " + id +  " not found");
				}
		}

	@Transactional
	public UserDTO insert(@Valid UserDTO dto) {
		User entity = new User();
		copyToEntity(entity, dto);
		entity = userRepository.save(entity);
		
		return new UserDTO(entity);
	}

	@Transactional
	public void delete(Long id) {
		if(!userRepository.existsById(id)) {
			throw new ResourceNotFoundException ("User " + id + "not found");
		}
		try {
			userRepository.deleteById(id);
		}
    	catch (DataIntegrityViolationException e) {
        	throw new DatabaseException("Integrity violation");
    	}
	}
	
	private void copyToEntity(User entity, UserDTO dto) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());

		entity.getRoles().clear();
		for(RoleDTO dtos : dto.getRoles()) {
			//Set<Category> cats = new HashSet<>();
			Role role = roleRepository.getReferenceById(dtos.getId());
			entity.getRoles().add(role);
		}
	}

}


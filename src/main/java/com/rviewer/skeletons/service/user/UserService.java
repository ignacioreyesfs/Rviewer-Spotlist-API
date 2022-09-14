package com.rviewer.skeletons.service.user;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rviewer.skeletons.dao.RoleRepository;
import com.rviewer.skeletons.dao.UserRepository;
import com.rviewer.skeletons.exception.APIError;
import com.rviewer.skeletons.exception.DuplicateResourceException;
import com.rviewer.skeletons.exception.ResourceNotFoundException;
import com.rviewer.skeletons.mapper.UserMapper;
import com.rviewer.skeletons.model.Role;
import com.rviewer.skeletons.model.User;

@Service
public class UserService implements UserDetailsService{
	private UserMapper userMapper;
	private UserRepository userRepo;
	private PasswordEncoder encoder;
	private RoleRepository roleRepo;
	
	public UserService(UserMapper userMapper, UserRepository userRepo, @Lazy PasswordEncoder encoder,
			RoleRepository roleRepo) {
		super();
		this.userMapper = userMapper;
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.roleRepo = roleRepo;
	}

	@Transactional
	public UserDTO save(UserRegisterDTO userRegister) {
		User user;
		
		if(userRepo.findByUsername(userRegister.getName()) != null) {
			throw new DuplicateResourceException(APIError.USER_WITH_SAME_NAME);
		}
		
		Role role = roleRepo.findByName("ROLE_USER");
		
		if(role == null) {
			role = roleRepo.save(new Role("ROLE_USER"));
		}
		
		user = userMapper.fromUserRegisterDTO(userRegister);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(role));
		return userMapper.toUserDTO(userRepo.save(user));
	}
	
	@Transactional
	public User findById(Long userId) {
		return userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(APIError.USER_NOT_FOUND));
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Username does not exists");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				true, true, true, true, mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
	}

}

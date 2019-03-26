package com.msg.msg.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msg.msg.encryption.CryptoConverter;
import com.msg.msg.entities.Login;
import com.msg.msg.entities.Token;
import com.msg.msg.entities.User;
import com.msg.msg.repositories.TokenRepository;
import com.msg.msg.repositories.UserRepository;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public TokenRepository tokenRepository;

//	@PostMapping("/user")
//	public Token loginUser(@RequestBody Login login) {
//		String username = login.getUsername();
//		String password = login.getPassword();
//		User user = userRepository.findByUsernameAndPassword(username, CryptoConverter.encrypt(password));
//		Token token = DatabaseHelper.createToken(user);
//		return token;
//	}

	@PostMapping("/user")
	public Token loginUser(@RequestBody Login login) {
		String username = login.getUsername();
		String password = login.getPassword();
		User user = userRepository.findByUsernameAndPassword(username, CryptoConverter.encrypt(password));
		String alphanumeric = UUID.randomUUID().toString();
//		tokenRepository.createToken(alphanumeric, user.getId());
		Token token = new Token(alphanumeric, user);
		tokenRepository.save(token);
//		Token token = tokenRepository.findByAlphanumeric(alphanumeric);
		return token;
	}

	@GetMapping("/userFromToken")
	public User getFromToken(@RequestHeader("X-MSG-AUTH") String alphanumeric) {
		int userId = tokenRepository.getUserIDFromTokenAlphaNumeric(alphanumeric);
		return userRepository.findById(userId);
	}

	@PostMapping("/logout")
	public void logout(@RequestHeader("X-MSG-AUTH") String alphanumeric) {
//		DatabaseHelper.logOutUser(alphanumeric);
		tokenRepository.deleteByAlphanumeric(alphanumeric);
	}
}

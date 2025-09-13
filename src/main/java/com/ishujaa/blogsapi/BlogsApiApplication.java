package com.ishujaa.blogsapi;

import com.ishujaa.blogsapi.model.User;
import com.ishujaa.blogsapi.model.type.RoleType;
import com.ishujaa.blogsapi.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
public class BlogsApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogsApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}

package com.joaog.apimongodb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.joaog.apimongodb.domain.Post;
import com.joaog.apimongodb.domain.User;
import com.joaog.apimongodb.dto.AutorDTO;
import com.joaog.apimongodb.dto.CommentDTO;
import com.joaog.apimongodb.repository.PostRepository;
import com.joaog.apimongodb.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		/* Limpa as coleções existentes */
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2020"), "Partiu Viagem!", "Vou viajar para São Paulo, abraços.", new AutorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2020"), "Bom dia", "Acordei feliz hoje!", new AutorDTO(maria));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2020"), new AutorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/03/2020"), new AutorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2020"), new AutorDTO(alex));
		
		post1.getComentarios().addAll(Arrays.asList(c1, c2));
		post2.getComentarios().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
	}
}
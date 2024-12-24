package vttp.batch5.miniproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.batch5.miniproject.services.BookService;

@SpringBootApplication
public class MiniprojectApplication {

	@Autowired
	BookService bookservice;

	public static void main(String[] args)  {
		SpringApplication.run(MiniprojectApplication.class, args);
	}



}

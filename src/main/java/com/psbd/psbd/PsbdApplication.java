package com.psbd.psbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan()
public class PsbdApplication {	
	public static void main(String[] args) {
		SpringApplication.run(PsbdApplication.class, args);
	}

}

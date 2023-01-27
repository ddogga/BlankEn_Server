package blank.english;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnglishApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnglishApplication.class, args);

		Hello hello = new Hello();
		hello.setGreeting("hi");
		System.out.println("hello.getGreeting() = " + hello.getGreeting());
	}

}

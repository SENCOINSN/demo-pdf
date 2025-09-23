package com.gl.si;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class DemoPdfApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoPdfApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Date dateE = new Date();

		Instant instant = dateE.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		int heure = 9;
		int minute = 0;

		LocalDateTime dateTime = localDate.atTime(heure, minute);

		LocalDateTime dateEnd = dateTime.plusHours(8);

		IO.println("date debut "+dateTime);
		IO.println("date fin "+dateEnd);

	}
}

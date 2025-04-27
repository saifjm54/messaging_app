package com.example.messaging_service;

import example.avro.User;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class MessagingServiceApplication {


	public static void main(String[] args) throws IOException {
		User user1 = new User();
		user1.setName("Alyssa");
		user1.setFavoriteNumber(256);

		User user2 = new User("Ben",7,"red");
		User user3 = User.newBuilder()
						.setName("Charlie")
								.setFavoriteColor("blue")
										.setFavoriteNumber(null)
												.build();
		DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
		dataFileWriter.create(user1.getSchema(),new File("users.avro"));
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.append(user3);
		dataFileWriter.close();
		SpringApplication.run(MessagingServiceApplication.class, args);
	}

}

package com.scm.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.scm.services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	// @Autowired
	// private EmailService service;

	// @Test
	// void sendEmailTest(){
	// 	service.sendEmail("rubanpreetsingh75@gmail.com", "Testing Email Service 2", "This is scm project working on email service");
	// }
}

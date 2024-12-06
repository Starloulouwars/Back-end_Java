package com.example.demo;

import com.example.demo.model.Utilisateur;
import com.mysql.cj.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class TodoListApplicationTests {

	public	void defininir_le_code_obliger() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setCode(("azerty"));

		Assertions.assertEquals("AZERTY", utilisateur.getCode());
	}

}

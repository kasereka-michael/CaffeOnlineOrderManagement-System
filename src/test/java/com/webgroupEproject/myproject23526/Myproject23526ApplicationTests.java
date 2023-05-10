package com.webgroupEproject.myproject23526;

import com.webgroupEproject.myproject23526.Model.Document;
import com.webgroupEproject.myproject23526.Repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Myproject23526ApplicationTests {
 		@Autowired
		private DocumentRepository documentRepository;

		 @Autowired
		 private TestEntityManager testEntityManager;

	@Test
	void TestinsertDocument() throws IOException {
		File file = new File("C:\\Users\\micha\\Pictures\\313259419_136484435836732_9171579528602420885_n.jpg");
		Document document= new Document();

		document.setName(file.getName());

		byte[] bytes = Files.readAllBytes(file.toPath());
		document.setContent(bytes);
		long size = bytes.length;
		document.setSize(size);

		Document savedoc = documentRepository.save(document);

		Document existd = testEntityManager.find(Document.class, savedoc.getId());




	}

}

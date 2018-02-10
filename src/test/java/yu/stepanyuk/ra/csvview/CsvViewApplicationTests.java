package yu.stepanyuk.ra.csvview;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvViewApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void uuidTest(){

		for (int i = 0; i < 3; i++) {
			UUID uuid = UUID.randomUUID();
			String strUiid = uuid.toString();
			System.out.println(strUiid);
		}

	}

}

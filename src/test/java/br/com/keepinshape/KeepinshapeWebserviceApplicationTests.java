package br.com.keepinshape;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.keepinshape.config.DbEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeepinshapeWebserviceApplication.class, DbEnvironment.class})
public class KeepinshapeWebserviceApplicationTests {

	@Test
	public void contextLoads() {
	}
}

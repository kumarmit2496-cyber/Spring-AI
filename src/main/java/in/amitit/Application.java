package in.amitit;

import in.amitit.service.OpenAIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	/*
	 * private final OpenAIService openAIService;
	 * 
	 * Application(OpenAIService openAIService) { this.openAIService =
	 * openAIService; }
	 */

	public static void main(String[] args) {
	
		SpringApplication.run(Application.class, args);
		
		
		
       //    ConfigurableApplicationContext  context= SpringApplication.run(Application.class, args);
			/*
			 * OpenAIService bean = context.getBean(OpenAIService.class);
			 * bean.askanything("question");
			 */
            
		
		
		
		
		
		
	}

}

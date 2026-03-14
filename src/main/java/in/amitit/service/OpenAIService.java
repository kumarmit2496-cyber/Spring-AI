	package in.amitit.service;
	
	import java.util.List;
	import java.util.Map;
	
	import org.springframework.ai.chat.client.ChatClient;
	import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
	import org.springframework.ai.chat.memory.ChatMemory;
	import org.springframework.ai.chat.prompt.Prompt;
	import org.springframework.ai.chat.prompt.PromptTemplate;
	import org.springframework.ai.image.ImageModel;
	import org.springframework.ai.image.ImageOptionsBuilder;
	import org.springframework.ai.image.ImagePrompt;
	import org.springframework.ai.image.ImageResponse;
	import org.springframework.stereotype.Service;
	
	@Service
	public class OpenAIService {
		
		//spring ai interface (impl class will be provided by LLM provider)
		private ChatClient chatClient;
	
		private ImageModel imageModel;
		
		
		  public OpenAIService(ChatClient.Builder builder,
                  ChatMemory chatMemory,
                  ImageModel imageModel) {

 this.chatClient = builder
         .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
         .build();

 this.imageModel = imageModel;
}
		
		
/*
 * public OpenAIService(ChatClient.Builder builder,ChatMemory chatMemory) {
 * chatClient=builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(
 * chatMemory).build()).build(); }
 */
		
		
		
		public String askanything(String question) {
		   return chatClient.prompt().user(question).call().content();
		}
		
		
			public String getTravelGuidance(String place,String month,String language,String budget) {
				PromptTemplate promptTemplate=new PromptTemplate("""
				        Welcome to the {place} travel guide!
				        If you're visiting in {month}, here's what you can do:
				        1. Must-visit attractions.
				        2. Local cuisine you must try.
				        3. Useful phrases in {language}.
				        4. Tips for traveling on a {budget} budget.
		
				        Enjoy your trip!
				        """);
				
				Prompt prompt=promptTemplate.create(Map.of("place",place,"month",month,"language",language,"budget",budget));
				
				return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
			}
			
		
		public String generateImage(String prompt) {
			           
			ImageResponse imageResponse=
					imageModel.call(new ImagePrompt(prompt,ImageOptionsBuilder.builder().
							height(1024).
							width(1024).
							N(1).
							build()));
			
			return imageResponse.getResult().getOutput().getUrl();
					
			
		}
	
		
		public String generateEmail(String subjet,String tone,String recipient) {
			
			PromptTemplate template=new PromptTemplate("""
					 Write a professional email.

                     Subject: {subject}
                     Recipient: {recipient}
                     Tone: {tone}

                    Generate a complete email including greeting and closing.
					""");
			
			Prompt prompt=template.create(
					Map.of(
							"subject", subjet,
							"tone", tone,
							"recipient", recipient
							
							));
			
			
			return chatClient.prompt(prompt)
					  .call()
					  .chatResponse()
					  .getResult()
			           .getOutput()
			           .getText();
			
		}
	}

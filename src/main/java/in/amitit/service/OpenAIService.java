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

	// spring ai interface (impl class will be provided by LLM provider)
	private ChatClient chatClient;

	private ImageModel imageModel;

	public OpenAIService(ChatClient.Builder builder, ChatMemory chatMemory, ImageModel imageModel) {

		this.chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();

		this.imageModel = imageModel;
	}

	/*
	 * public OpenAIService(ChatClient.Builder builder,ChatMemory chatMemory) {
	 * chatClient=builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(
	 * chatMemory).build()).build(); }
	 */

	public String askanything(String question) {

		/*
		 * String prompt = """ Explain the topic in a clean, well-structured and
		 * readable format.
		 * 
		 * Rules: - Do NOT return code blocks - Do NOT mention HTML - Use simple text
		 * formatting - Use headings and bullet points - Keep it clean and readable like
		 * study notes - Avoid too many spaces - Highlight important headings using
		 * emojis or symbols
		 * 
		 * Structure: 1. Short introduction (2–3 lines) 2. Key Highlights (bullet
		 * points) 3. Advantages 4. Modern Usage 5. Considerations
		 * 
		 * Topic: """ + question;
		 */

		return chatClient.prompt().user(question).call().content();
	}

	public String getTravelGuidance(String place, String month, String language, String budget) {
		PromptTemplate promptTemplate = new PromptTemplate("Welcome to the {place} travel guide!\n"
				+ "If you're visiting in {month}, here's what you can do:\n" + "1. Must-visit attractions.\n"
				+ "2. Local cuisine you must try.\n" + "3. Useful phrases in {language}.\n"
				+ "4. Tips for traveling on a {budget} budget.\n" + "Enjoy your trip!");

		Prompt prompt = promptTemplate
				.create(Map.of("place", place, "month", month, "language", language, "budget", budget));

		return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
	}

	public String generateImage(String prompt) {

		ImageResponse imageResponse = imageModel
				.call(new ImagePrompt(prompt, ImageOptionsBuilder.builder().height(1024).width(1024).N(1).build()));

		return imageResponse.getResult().getOutput().getUrl();

	}

	public String generateEmail(String subjet, String tone, String recipient) {

		PromptTemplate template = new PromptTemplate("""
				Write a professional business email in clean HTML format.

				Requirements:
				- Use proper structure (Subject, Greeting, Body, Closing)
				- Use <br> for line breaks
				- Make Subject bold using <b>
				- Keep tone strictly {tone}
				- Keep it concise and professional
				- Do NOT use placeholders like [Name], generate realistic content

				Input:
				Subject: {subject}
				Recipient: {recipient}

				Output should be well-formatted HTML email.
				""");

		Prompt prompt = template.create(Map.of("subject", subjet, "tone", tone, "recipient", recipient

		));

		return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();

	}

	public String genearateResume(String name, String skills, String experience, String role) {

		PromptTemplate template = new PromptTemplate("""
			 Create a professional resume.

				Name: {name}
				Target Role: {role}
				Skills: {skills}
				Experience: {experience}

				IMPORTANT FORMATTING RULES:
				- Each line MUST be separated using newline (\n)
				- Do NOT write everything in one paragraph
				- Keep each section clearly separated

				FORMAT EXACTLY LIKE THIS:

				Name: {name}
				Role: {role}

				SUMMARY:
				Write 2-3 lines.

				SKILLS:
				- Skill 1
				- Skill 2
				- Skill 3

				EXPERIENCE:
				- Point 1
				- Point 2
				- Point 3

				PROJECTS:
				- Project 1
				- Project 2

				EDUCATION:
				1-2 lines only.

				STRICT RULE:
				Return output with proper line breaks only.
				No markdown (** , --- , #).
				""");
		Prompt prompt = template.create(Map.of(

				"name", name, "skills", skills, "experience", experience, "role", role

		));
		String rawOutput = chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
		return rawOutput.replace("\\n", "\n");

	}
}

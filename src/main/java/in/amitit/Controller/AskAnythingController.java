package in.amitit.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.amitit.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AskAnythingController {
	
	@Autowired
	private OpenAIService openAIService;
	
	
	@GetMapping("/")
	public String showAskAnythingPage(Model model) {
		return "askAnything";
		
	}
	

	@PostMapping("/askAnything")
	public String handleAskAnything(@RequestParam String question,Model model) {
		String answer=openAIService.askanything(question);
		model.addAttribute("question", question);
		model.addAttribute("answer", answer);
		return "askAnything";
		
	}

}

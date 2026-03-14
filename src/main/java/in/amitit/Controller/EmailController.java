package in.amitit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.amitit.service.OpenAIService;

@Controller
public class EmailController {
	
	    
	@Autowired
	private OpenAIService openAIService;
	
	
	@GetMapping("/emailWriter")
	public String showEmailPage() {
		return "emailWriter";
	}
	
	@PostMapping("/emailWriter")
	public String generateEmail(@RequestParam String subject,
			                    @RequestParam String tone,
			                    @RequestParam String recipient,
			                    Model model) {
		
		         String email = openAIService.generateEmail(subject, tone, recipient);
		         model.addAttribute("email",email);
		return "emailWriter";
		
	}

}

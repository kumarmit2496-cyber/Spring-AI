package in.amitit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.amitit.service.OpenAIService;

@Controller
public class ImageGenerationController {

   
	
	
	@Autowired
	private OpenAIService openAIService;

   
	
	@GetMapping("/imageGenerator")
	public String showImageGeneartionPage() {
		return "imageGenerator";
	}
	
	
	@PostMapping("/imageGenerator")
	public String generateImage(@RequestParam String prompt,Model model) {
		         String imageurl = openAIService.generateImage(prompt);
		         System.out.println("Response: "+imageurl);
		         model.addAttribute("image",imageurl);
		         
		
		
		return "imageGenerator";
		
		
	}

}

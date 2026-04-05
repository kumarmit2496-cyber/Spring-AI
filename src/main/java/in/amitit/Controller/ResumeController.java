
package in.amitit.Controller;

import java.io.ByteArrayInputStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import in.amitit.service.OpenAIService;
import in.amitit.service.ResumePdfService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ResumeController {

	@Autowired
	private OpenAIService openAIService;
	
	@Autowired
	private ResumePdfService resumePdfService;

	@GetMapping("/resumeBuilder")
	public String showResumePage() {

		return "resumeBuilder";
	}
	
	@PostMapping("/resumeBuilder")
	public String generateresume(
			@RequestParam String name,
			@RequestParam String role,
			@RequestParam String skills,
			@RequestParam String experience,
			Model model,
			HttpSession session){
		
		  String resume = openAIService.genearateResume(name, skills, experience, role);
		  model.addAttribute("resume",resume);
		  
		  model.addAttribute("resume", resume);

		
		    session.setAttribute("resume", resume);
		  
		  return "resumeBuilder";
		
	}
	
	@GetMapping("/downloadResume")
	public ResponseEntity<byte[]> downloadResume(HttpSession session){
		String resume=(String)session.getAttribute("resume");	
		 if (resume == null || resume.isEmpty()) {
		        return ResponseEntity.badRequest()
		                .body("Resume not found. Please generate resume first.".getBytes());
		    }
			ByteArrayInputStream pdf=resumePdfService.generatepdf(resume);
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=resume.pdf");
			
		return ResponseEntity
				.ok()
				 .headers(headers)
				 .contentType(MediaType.APPLICATION_PDF)
				 .body(pdf.readAllBytes());
	}
	
	
	

}

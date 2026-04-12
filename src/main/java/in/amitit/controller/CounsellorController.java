package in.amitit.controller;

import in.amitit.dto.DashboardResponseDto;
import in.amitit.entities.Counsellor;
import in.amitit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @GetMapping("/")
    private String index(Model model){
        Counsellor cobj=new Counsellor();
        model.addAttribute("counsellor",cobj);
        return "index";
    }

    @PostMapping("/login")
    public String handlelogin(Counsellor counsellor, Model model, HttpServletRequest req){
     Counsellor c=counsellorService.login(counsellor.getEmail(),counsellor.getPwd());
     if(c==null){
         model.addAttribute("emsg","Wrong UserName or Password!!!");
         return  "index";
     }else {
         HttpSession session= req.getSession(true);
         session.setAttribute("CID",c.getCounsellorId());
         session.setAttribute("CNAME", c.getName());
         return "redirect:/dashboard";
     }
  }
   @GetMapping("/dashboard")
   public String buildDashboard(Model model,HttpServletRequest request){
        HttpSession session=request.getSession(false);
        Integer cid=(Integer) session.getAttribute("CID");

    DashboardResponseDto dashboardInfoDto=counsellorService.getDashboardInfo(cid);
    model.addAttribute("dashboardInfo",dashboardInfoDto);
    return "dashboard";
  }

   @GetMapping("/logout")
   public String logout(HttpServletRequest req){
        HttpSession session= req.getSession(false);
        if(session!= null) {
            session.invalidate();
        }
        return "redirect:/";
   }

    @GetMapping("/register")
    public String register(Model model){
        Counsellor cobj=new Counsellor();
        model.addAttribute("counsellor",cobj);
        return "register";
    }

     @PostMapping("/register")
    public String haddleRegistration(Counsellor counsellor,Model model){
        if(!counsellorService.isEmailUnique(counsellor.getEmail())){
            model.addAttribute("emsg","Duplicate Email found");
            return "register";
        }
        boolean registered=counsellorService.register(counsellor);
        if(registered){
            model.addAttribute("smsg","Registration successful, please login");

        }else {
            model.addAttribute("emsg","Registartion failed, try again");
        }
        return "register";
    }

}

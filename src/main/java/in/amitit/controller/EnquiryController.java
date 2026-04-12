package in.amitit.controller;

import in.amitit.dto.EnqFilterRequestDto;
import in.amitit.dto.EnquiryDto;
import in.amitit.entities.Enquiry;
import in.amitit.service.CourseService;
import in.amitit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EnquiryController {

        @Autowired
        private EnquiryService enquiryService;

         @Autowired
         private CourseService courseService;

         @GetMapping("/enquiry")
         public  String enquiryForm(Model model){

             EnquiryDto enquiryDtoObj=new EnquiryDto();
             model.addAttribute("enquiry",enquiryDtoObj);
             model.addAttribute("courses",courseService.getCourse());

             return "add-enq";

         }
         @PostMapping("/enquiry")
         public String addEnquiry(@ModelAttribute ("enquiry") EnquiryDto enquiryDto, Model model, HttpServletRequest request){
             HttpSession session=request.getSession(false);
             Integer cid=(Integer) session.getAttribute("CID");
             boolean status=enquiryService.addEnquiry(enquiryDto,cid);
             if(status){
                 model.addAttribute("successMsg","Enquiry Added Successfully!");
             }else {
                 model.addAttribute("errorMsg","Enquiry Not Saved!");
             }

             model.addAttribute("courses",courseService.getCourse());
             return "add-enq";
         }
         @GetMapping("/view-enquiries")
         public  String viewEnquiries(Model model,HttpServletRequest request){
             HttpSession session=request.getSession(false);
             Integer cid=(Integer)session.getAttribute("CID");

             model.addAttribute("filterRequestDto", new EnqFilterRequestDto());
             model.addAttribute("enqs",enquiryService.getAllEnquiries(cid));
             model.addAttribute("courses",courseService.getCourse());
             return "view-enqs";
         }
         @PostMapping("/filter-eqnuiries")
         public String viewEnquiries(@ModelAttribute("filterRequestDto") EnqFilterRequestDto filterRequestDto, Model model, HttpServletRequest request){
             HttpSession session=request.getSession();
             Integer cid=  (Integer)session.getAttribute("CID");

                   List<Enquiry> enquiriesWithFilter =enquiryService.getEnquiriesWithFilter(filterRequestDto,cid);
               model.addAttribute("enqs",enquiriesWithFilter);
               model.addAttribute("courses",courseService.getCourse());

               return "view-enqs";



         }
        @GetMapping("/editenq")
         public String  editEnquiry(@RequestParam("enqId") Integer enqId,Model model){
                     Enquiry enquiryById=enquiryService.getEnquiryById(enqId);

                     EnquiryDto enquirydto=new EnquiryDto();
             BeanUtils.copyProperties( enquiryById,enquirydto);
             enquirydto.setCourseId(enquiryById.getCourse().getCourseId());
             model.addAttribute("enquiry",enquirydto);
             model.addAttribute("courses",courseService.getCourse());

             return "add-enq";


         }





}

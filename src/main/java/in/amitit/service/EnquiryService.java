package in.amitit.service;

import in.amitit.dto.EnqFilterRequestDto;
import in.amitit.dto.EnquiryDto;
import in.amitit.entities.Enquiry;

import java.util.List;

public interface EnquiryService {

    public boolean addEnquiry(EnquiryDto enquiryDto,Integer counsellorId);
    public List<Enquiry> getAllEnquiries(Integer counsellorId);
    public List<Enquiry> getEnquiriesWithFilter(EnqFilterRequestDto filterRequestDto,Integer counsellorId);
    public Enquiry getEnquiryById(Integer enqId);
    public boolean updateEnquiry(EnquiryDto enquiryDto);

}

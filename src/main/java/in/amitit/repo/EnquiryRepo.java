package in.amitit.repo;

import in.amitit.entities.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry,Integer> {

    public List<Enquiry> findByCounsellorCounsellorId(Integer CounselloarId);


}

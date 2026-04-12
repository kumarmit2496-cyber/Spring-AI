package in.amitit.repo;

import in.amitit.entities.Counsellor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {

    public Optional<Counsellor> findByEmail(String email);

    public Optional<Counsellor>  findByEmailAndPwd(String email,String pwd);
}

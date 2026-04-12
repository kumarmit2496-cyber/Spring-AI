package in.amitit.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "counsellor")
@Setter
@Getter
public class Counsellor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counsellorId;
    private String name;
    private String email;
    private String pwd;
    private String phno;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;



}

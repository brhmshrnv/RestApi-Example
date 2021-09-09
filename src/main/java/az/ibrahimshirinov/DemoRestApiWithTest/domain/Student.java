package az.ibrahimshirinov.DemoRestApiWithTest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    @PrePersist
    public void createdAt(){
        this.createdAt=LocalDateTime.now();
    }

    @PreUpdate
    public void modifiedAt(){
        this.modifiedAt = LocalDateTime.now();
    }
}

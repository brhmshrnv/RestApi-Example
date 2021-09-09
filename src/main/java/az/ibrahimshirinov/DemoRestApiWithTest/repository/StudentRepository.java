package az.ibrahimshirinov.DemoRestApiWithTest.repository;

import az.ibrahimshirinov.DemoRestApiWithTest.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student,Long> {
}

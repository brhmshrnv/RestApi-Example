package az.ibrahimshirinov.DemoRestApiWithTest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRequestDto {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
}

package az.ibrahimshirinov.DemoRestApiWithTest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

package az.ibrahimshirinov.DemoRestApiWithTest.api;

import az.ibrahimshirinov.DemoRestApiWithTest.dto.StudentRequestDto;
import az.ibrahimshirinov.DemoRestApiWithTest.dto.response.StudentResponseDto;
import az.ibrahimshirinov.DemoRestApiWithTest.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    @PostMapping
    public ResponseEntity<StudentResponseDto> create(@RequestBody StudentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(id));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StudentResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageable));
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> update(@PathVariable Long id, @RequestBody StudentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

package az.ibrahimshirinov.DemoRestApiWithTest.service;

import az.ibrahimshirinov.DemoRestApiWithTest.domain.Student;
import az.ibrahimshirinov.DemoRestApiWithTest.dto.StudentRequestDto;
import az.ibrahimshirinov.DemoRestApiWithTest.dto.response.StudentResponseDto;
import az.ibrahimshirinov.DemoRestApiWithTest.exception.StudentNotFoundException;
import az.ibrahimshirinov.DemoRestApiWithTest.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository repository;

    public StudentResponseDto create(StudentRequestDto dto) {
        log.info("Operation: Create student");
        Student saved = repository.save(modelMapper.map(dto, Student.class));
        return modelMapper.map(saved,StudentResponseDto.class);
    }

    public StudentResponseDto get(Long id) {
        log.info("Operation: Get student with id: {}",id);
       return repository.findById(id)
                .map( student ->  modelMapper.map(student,StudentResponseDto.class))
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Page<StudentResponseDto> getAll(Pageable pageable) {
        log.info("Operation: Get all students");
        return repository.findAll(pageable)
                .map(student -> modelMapper.map(student,StudentResponseDto.class));
    }

    public StudentResponseDto update(Long id, StudentRequestDto dto) {
        log.info("Operation: Update student with id: {}",id);
      return   repository.findById(id)
                .map(student -> {
                    Student updatedStudent = repository.save(modelMapper.map(dto, Student.class));
                    return modelMapper.map(updatedStudent,StudentResponseDto.class);
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public void delete(Long id) {
        log.info("Operation: Deleted student with id: {}",id);

        isExist(id);
        repository.deleteById(id);
    }

    public void isExist(Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
    }
}


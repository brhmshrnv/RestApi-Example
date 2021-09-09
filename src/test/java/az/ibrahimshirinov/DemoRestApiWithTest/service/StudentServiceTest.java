package az.ibrahimshirinov.DemoRestApiWithTest.service;

import az.ibrahimshirinov.DemoRestApiWithTest.domain.Student;
import az.ibrahimshirinov.DemoRestApiWithTest.dto.StudentRequestDto;
import az.ibrahimshirinov.DemoRestApiWithTest.dto.response.StudentResponseDto;
import az.ibrahimshirinov.DemoRestApiWithTest.exception.StudentNotFoundException;
import az.ibrahimshirinov.DemoRestApiWithTest.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private static final Long DUMMY_ID = 1L;
    private static final String DUMMY_STRING = "string";
    private static final Integer DUMMY_INTEGER = 1;

    @Mock
    private StudentRepository repository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentService service;

    private StudentRequestDto studentRequestDto;
    private StudentResponseDto studentResponseDto;
    private Student student;

    @BeforeEach
    void setUp() {
        studentRequestDto = getStudentRequestDto();
        studentResponseDto = getStudentResponseDto();
        student = getStudent();
    }

    @Test
    @DisplayName("Save student successfully")
    void givenStudentRequestDtoWhenCreateThenStudentResponseDto() {
        //Arrange
        when(repository.save(student)).thenReturn(student);
        //Act
        StudentResponseDto result = modelMapper.map(service.create(studentRequestDto), StudentResponseDto.class);
        //Assert
        assertThat(result).isEqualTo(studentResponseDto);
        //Verify
        verify(repository,times(1)).save(student);

    }

    @Test
    @DisplayName("Trying to get student but throws exception")
    void givenInvalidStudentIdWhenGetThenException() {
        //Arrange
        when(repository.findById(DUMMY_ID)).thenReturn(Optional.empty());
        //Act & Assert
        assertThatThrownBy(() -> service.get(DUMMY_ID)).isInstanceOf(StudentNotFoundException.class);
        //Verify
        verify(repository,times(1)).findById(DUMMY_ID);
    }

    @Test
    @DisplayName("Get student successfully")
    void givenValidStudentIdWhenGetThenStudentResponseDto() {
        //Arrange
        when(repository.findById(DUMMY_ID)).thenReturn(Optional.of(student));
        //Act
        StudentResponseDto result = service.get(DUMMY_ID);
        //Assert
        assertThat(result).isEqualTo(studentResponseDto);
    }

    @Test
    @DisplayName("Get all students pageable")
    void givenPageNumberAndSizeWhenSearchThenExpectStudentResponseDto() {
        //Arrange
        when(repository.findAll(Pageable.unpaged())).thenReturn(Page.empty());
        //Act
        service.getAll(Pageable.unpaged());
        //Verify
        verify(repository,times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Trying to update student but throws exception")
    void givenInvalidStudentIdWhenUpdateThenException() {
        //Arrange
        when(repository.findById(DUMMY_ID)).thenReturn(Optional.empty());
        //Act & Assert
        assertThatThrownBy(() -> service.update(DUMMY_ID, studentRequestDto))
                .isInstanceOf(StudentNotFoundException.class);
        //Verify
        verify(repository,times(1)).findById(DUMMY_ID);
    }

    @Test
    @DisplayName("Update student successfully")
    void givenValidStudentIdWhenUpdateThenStudentResponseDto() {
        //Arrange
        when(repository.findById(DUMMY_ID)).thenReturn(Optional.of(student));
        when(repository.save(student)).thenReturn(student);
        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
        //Act
        StudentResponseDto result = service.update(DUMMY_ID, studentRequestDto);
        //Assert
        assertThat(result).isEqualTo(studentResponseDto);
        //Verify
        verify(repository, times(1)).findById(DUMMY_ID);
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Check student exist but throws exception")
    void givenInvalidStudentIdWhenExistThenException() {
        //Arrange
        when(repository.existsById(DUMMY_ID)).thenReturn(false);
        //Act & Assert
        assertThatThrownBy(() -> service.isExist(DUMMY_ID)).isInstanceOf(StudentNotFoundException.class);
        //Verify
        verify(repository, times(1)).existsById(DUMMY_ID);

    }

    @Test
    @DisplayName("Delete student successfully")
    void givenValidStudentIdWhenDeleteThenOk() {
        //Arrange
        when(repository.existsById(DUMMY_ID)).thenReturn(true);
        //Act
        service.delete(DUMMY_ID);
        //Verify
        verify(repository, times(1)).deleteById(DUMMY_ID);
    }

    private StudentRequestDto getStudentRequestDto() {
        return StudentRequestDto.builder()
                .id(DUMMY_ID)
                .name(DUMMY_STRING)
                .surname(DUMMY_STRING)
                .age(DUMMY_INTEGER)
                .build();
    }

    private StudentResponseDto getStudentResponseDto() {
        return StudentResponseDto.builder()
                .id(DUMMY_ID)
                .name(DUMMY_STRING)
                .surname(DUMMY_STRING)
                .age(DUMMY_INTEGER)
                .build();
    }

    private Student getStudent() {
        return Student.builder()
                .id(DUMMY_ID)
                .name(DUMMY_STRING)
                .surname(DUMMY_STRING)
                .age(DUMMY_INTEGER)
                .build();
    }


}
package az.ibrahimshirinov.DemoRestApiWithTest.api;

import az.ibrahimshirinov.DemoRestApiWithTest.domain.Student;
import az.ibrahimshirinov.DemoRestApiWithTest.dto.StudentRequestDto;
import az.ibrahimshirinov.DemoRestApiWithTest.dto.response.StudentResponseDto;
import az.ibrahimshirinov.DemoRestApiWithTest.exception.StudentNotFoundException;
import az.ibrahimshirinov.DemoRestApiWithTest.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    private static final Long DUMMY_ID = 1L;
    private static final String DUMMY_STRING = "string";
    private static final Integer DUMMY_INTEGER = 1;
    private static final String BASE_URL = "/student";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService service;

    private StudentResponseDto studentResponseDto;
    private StudentRequestDto studentRequestDto;

    @BeforeEach
    void setUp() {
        studentRequestDto = getStudentRequestDto();
        studentResponseDto = getStudentResponseDto();
    }


    @Test
    @DisplayName("Http:POST save student")
    void givenValidInputWhenCreateThenReturnOk() throws Exception {

        //Arrange
        when(service.create(studentRequestDto)).thenReturn(studentResponseDto);
        //Act
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(studentRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //Assert
        resultActions.andExpect(status().isCreated());
        //Verify
        verify(service, times(1)).create(studentRequestDto);
    }

    @Test
    @DisplayName("Http:GET get student but throw exception")
    void givenInvalidInputWhenGetThenException() throws Exception {
        //Arrange
        String stringId = "id";

        //Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + stringId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
    }

    @Test
    @DisplayName("Http:GET get student")
    void givenValidInputWhenGetThenReturnOk() throws Exception {
        //Arrange
        when(service.get(DUMMY_ID)).thenReturn(studentResponseDto);
        //Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + DUMMY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Http:GET get all students")
    void givenValidPageNumberAndSizeWhenGetAllThenReturnOk() throws Exception {
        //Arrange
        Page<StudentResponseDto> studentDtoPage = Page.empty();

        //Act
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/page")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDtoPage)));

        //Assert
        actions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Http:PUT update student successfully")
    void givenValidInputWhenUpdateThenReturnOk() throws Exception {
        //Arrange
        when(service.update(DUMMY_ID, studentRequestDto)).thenReturn(studentResponseDto);

        //Act
        ResultActions resultActions = mockMvc.perform(put(BASE_URL + "/" + DUMMY_ID)
                .content(objectMapper.writeValueAsString(studentRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //Assert
        resultActions.andExpect(status().isOk());

        //Verify
        verify(service, times(1)).update(DUMMY_ID, studentRequestDto);
    }

    @Test
    @DisplayName("Http:DELETE delete student successfully")
    void givenValidIdWhenDeleteThenNoContent() throws Exception {

        //Act
        ResultActions resultActions = mockMvc.perform(delete(BASE_URL + "/" + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        resultActions.andExpect(status().isNoContent());

        //Verify
        verify(service, times(1)).delete(DUMMY_ID);
    }

    @Test
    @DisplayName("Http:DELETE delete student but throws exception")
    void givenInvalidIdWhenDeleteThenExpectBadRequest() throws Exception {
        //Arrange
        String id = "id";

        //Act
        ResultActions resultActions = mockMvc.perform(delete(BASE_URL + "/" + id )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
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


}
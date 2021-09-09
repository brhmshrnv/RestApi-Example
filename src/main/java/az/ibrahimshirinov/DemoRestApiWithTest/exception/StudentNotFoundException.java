package az.ibrahimshirinov.DemoRestApiWithTest.exception;

import az.ibrahimshirinov.DemoRestApiWithTest.exception.common.NotFoundException;

public class StudentNotFoundException extends NotFoundException {

    public static  final String MESSAGE="Student not found %s";
    private static final long serialVersionUID = 1L;

    public StudentNotFoundException(Long id) {
        super(String.format(MESSAGE,id));
    }
}

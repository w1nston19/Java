package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.test.response;

import exceptions.BadRequestException;
import exceptions.ServerErrorException;
import exceptions.TooManyRequestsException;
import exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

    @SuppressWarnings("unchecked")
    private static final HttpResponse<String> mockRequest =
            (HttpResponse<String>) Mockito.mock(HttpResponse.class);
    private ErrorHandler testedErrorHandler;


    static {
        when(mockRequest.body()).thenReturn("{\n" +
                "\"status\": \"error\",\n" +
                "\"code\": \"customCode\",\n" +
                "\"message\": \"custom message\"\n" +
                "}");
    }

    {
        testedErrorHandler = ErrorHandler.of(mockRequest);
    }

    @Test
    void testCode() {
        when(mockRequest.statusCode()).thenReturn(123);
        testedErrorHandler = ErrorHandler.of(mockRequest);
        assertEquals("customCode", testedErrorHandler.getCode());
        assertEquals(mockRequest.statusCode(), testedErrorHandler.getStatus());
    }

    @Test
    void testThrowsBadRequestException() {
        when(mockRequest.statusCode()).thenReturn(400);
        testedErrorHandler = ErrorHandler.of(mockRequest);
        assertThrows(BadRequestException.class,
                () -> testedErrorHandler.handleError());
    }

    @Test
    void testThrowsServerErrorException() {
        when(mockRequest.statusCode()).thenReturn(500);
        testedErrorHandler = ErrorHandler.of(mockRequest);
        assertThrows(ServerErrorException.class,
                () -> testedErrorHandler.handleError());
    }

    @Test
    void testThrowsTooManyRequestsException() {
        when(mockRequest.statusCode()).thenReturn(429);
        testedErrorHandler = ErrorHandler.of(mockRequest);
        assertThrows(TooManyRequestsException.class,
                () -> testedErrorHandler.handleError());
    }

    @Test
    void testThrowsUnauthorizedException() {
        when(mockRequest.statusCode()).thenReturn(401);
        testedErrorHandler = ErrorHandler.of(mockRequest);
        assertThrows(UnauthorizedException.class,
                () -> testedErrorHandler.handleError());
    }


    @Test
    void testExceptionWhenCodeIsUnknown() {
        when(mockRequest.statusCode()).thenReturn(789);
        testedErrorHandler = ErrorHandler.of(mockRequest);
        assertThrows(Exception.class,
                () -> testedErrorHandler.handleError());
    }
}
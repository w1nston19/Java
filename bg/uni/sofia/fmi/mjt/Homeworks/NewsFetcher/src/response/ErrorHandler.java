package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src.response;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.ServerErrorException;
import exceptions.TooManyRequestsException;
import exceptions.UnauthorizedException;

import java.net.http.HttpResponse;

public class ErrorHandler {

    private static final int BAD_REQUEST_CODE = 400;
    private static final int UNAUTHORIZED_CODE = 401;
    private static final int TOO_MANY_REQUESTS_CODE = 429;
    private static final int SERVER_ERROR_CODE = 500;
    public int getStatus() {
        return status;
    }

    transient private int status;
    private String code;
    private String message;

    public static ErrorHandler of(HttpResponse<String> errorResponse) {
        Gson gson = new Gson();
        ErrorHandler result = gson.fromJson(errorResponse.body(), ErrorHandler.class);
        result.status = errorResponse.statusCode();
        return result;
    }


    public void handleError() throws Exception {
        switch (status) {
            case BAD_REQUEST_CODE -> throw new BadRequestException(getMessage());
            case UNAUTHORIZED_CODE -> throw new UnauthorizedException(getMessage());
            case TOO_MANY_REQUESTS_CODE -> throw new TooManyRequestsException(getMessage());
            case SERVER_ERROR_CODE -> throw new ServerErrorException(getMessage());
            default -> throw new Exception("Unknown exception code");
        }
    }

    private String getMessage() {
        return " The request resulted in error :\n" +
                "Error code : %d (%s)%n".formatted(status, code) +
                "Message : %s%n".formatted(message);

    }


    public String getCode() {
        return code;
    }
}

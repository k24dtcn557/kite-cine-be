package vn.id.hph.kitecine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    CINEMA_NOT_FOUND(2001, "Không tìm thấy thông tin rạp", HttpStatus.NOT_FOUND),
    AUDITORIUM_NOT_FOUND(2002, "Không tìm thấy thông tin phòng chiếu", HttpStatus.NOT_FOUND),
    SEAT_NOT_FOUND(2003, "Không tìm thấy thông tin ghế", HttpStatus.NOT_FOUND),
    SEAT_ROW_ALREADY_EXISTED(2004, "Hàng ghế {0} đã tồn tại", HttpStatus.BAD_REQUEST),
    MOVIE_NOT_FOUND(2005, "Không tìm thấy thông tin phim", HttpStatus.NOT_FOUND),
    CREW_PERSON_NOT_FOUND(2006, "Không tìm thấy thông tin nhân sự", HttpStatus.NOT_FOUND),
    CREW_MEMBER_NOT_FOUND(2007, "Không tìm thấy thông tin thành viên đoàn phim", HttpStatus.NOT_FOUND),
    PRICE_MODEL_NOT_FOUND(2008, "Không tìm thấy thông tin price model", HttpStatus.NOT_FOUND),
    SHOW_TIME_NOT_FOUND(2009, "Không tìm thấy thông tin suất chiếu", HttpStatus.NOT_FOUND);
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

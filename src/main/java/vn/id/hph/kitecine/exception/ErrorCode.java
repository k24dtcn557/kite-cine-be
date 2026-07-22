package vn.id.hph.kitecine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Có lỗi xảy ra, vui lòng thử lại.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Có lỗi xảy ra, vui lòng thử lại.", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Tài khoản này đã tồn tại.", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Tên tài khoản phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Vui lòng đăng nhập để thực hiện thao tác này", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền thực thực hiện thao tác này", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Tuổi của bạn phải ít nhất {min}", HttpStatus.BAD_REQUEST),
    CINEMA_NOT_FOUND(2001, "Không tìm thấy thông tin rạp", HttpStatus.NOT_FOUND),
    AUDITORIUM_NOT_FOUND(2002, "Không tìm thấy thông tin phòng chiếu", HttpStatus.NOT_FOUND),
    SEAT_NOT_FOUND(2003, "Không tìm thấy thông tin ghế", HttpStatus.NOT_FOUND),
    SEAT_ROW_ALREADY_EXISTED(2004, "Hàng ghế này đã tồn tại", HttpStatus.BAD_REQUEST),
    MOVIE_NOT_FOUND(2005, "Không tìm thấy thông tin phim", HttpStatus.NOT_FOUND),
    CREW_PERSON_NOT_FOUND(2006, "Không tìm thấy thông tin thành viên đoàn phim", HttpStatus.NOT_FOUND),
    CREW_MEMBER_NOT_FOUND(2007, "Không tìm thấy thông tin thành viên đoàn phim", HttpStatus.NOT_FOUND),
    PRICE_MODEL_NOT_FOUND(2008, "Không tìm thấy thông tin bảng giá", HttpStatus.NOT_FOUND),
    SHOW_TIME_NOT_FOUND(2009, "Không tìm thấy thông tin suất chiếu", HttpStatus.NOT_FOUND),
    TICKET_NOT_FOUND(2010, "Không tìm thấy thông tin vé", HttpStatus.NOT_FOUND),
    TICKET_EXPIRED(2011, "Vé đã hết hạn giữ, vui lòng chọn lại", HttpStatus.BAD_REQUEST),
    PURCHASE_NOT_FOUND(2012, "Không tìm thấy thông tin đơn hàng", HttpStatus.NOT_FOUND),
    TICKET_NOT_SELECTED(2013, "Vui lòng chọn vé", HttpStatus.BAD_REQUEST),
    INVALID_OLD_PASSWORD(2014, "Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCHES(2015, "Mật khẩu mới và xác nhận mật khẩu không khớp", HttpStatus.BAD_REQUEST),
    NO_EMAIL_ADDRESS(2016, "Vui lòng cung cấp địa chỉ email", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(2017, "Gửi email thất bại", HttpStatus.INTERNAL_SERVER_ERROR);
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

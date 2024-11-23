package udpm.hn.metu.infrastructure.constant;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMessage {

    public static class Success {
        public static final String GET_SUCCESS = "Lấy dữ liệu thành công";
        public static final String CREATE_SUCCESS = "Tạo dữ liệu thành công";
        public static final String UPDATE_SUCCESS = "Cập nhật dữ liệu thành công";
        public static final String DELETE_SUCCESS = "Xóa dữ liệu thành công";

    }

    public static class Error {
        public static final String GET_ERROR = "Lấy dữ liệu thất bại";
        public static final String CREATE_ERROR = "Tạo dữ liệu thất bại";
        public static final String UPDATE_ERROR = "Cập nhật dữ liệu thất bại";
        public static final String DELETE_ERROR = "Xóa dữ liệu thất bại";
    }

    public static class Exception {
        public static final String CLIENT_NOT_FOUND = "Không tìm thấy khách hàng";
        public static final String CALL_API_FAIL = "Gọi API thất bại";
        public static final String CALL_API_SUCCESS = "Gọi API thành công";
        public static final String ERROR_REDIRECT_MODULE = "Lỗi khi chuyển hướng đến module! Vui lòng thử lại sau!";
    }

    public static class Response {
        public static final String INTERNAL_SERVER_ERROR = "Có lỗi xảy ra, vui lòng thử lại sau";
        public static final String NOT_FOUND = "Không tìm thấy dữ liệu";
        public static final String SUCCESS = "Thành công";
        public static final String INVALID_REQUEST = "Yêu cầu không hợp lệ";
        public static final String UNAUTHORIZED = "Không có quyền truy cập";
        public static final String FORBIDDEN = "Không được phép truy cập";
        public static final String BAD_REQUEST = "Yêu cầu không hợp lệ";
        public static final String CREATED = "Tạo mới thành công";
        public static final String UPDATED = "Cập nhật thành công";
        public static final String DELETED = "Xóa thành công";
        public static final String DUPLICATE = "Dữ liệu đã tồn tại";
        public static final String NOT_FOUND_USER = "Không tìm thấy người dùng";
        public static final String NOT_FOUND_ROLE = "Không tìm thấy quyền";
        public static final String STRING_TOO_LONG = "Dữ liệu quá dài";
        public static final String CODE_EXIST = "Mã đã tồn tại";
    }

}

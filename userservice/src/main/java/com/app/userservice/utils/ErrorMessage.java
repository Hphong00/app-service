package com.app.userservice.utils;

public enum ErrorMessage implements BaseMessage {
    OK("000", "OK"),
    UNHANDLED_ERROR("001", "Unhandled error", "%s"),
    OBJECT_NOT_FOUND("002", "Object not found", "%s không tồn tại"),
    OBJECT_ALREADY_EXIST("003", "Object already exist", "%s đã tồn tại"),
    TENANT_NOT_FOUND("004", "Tenant  not found or inactive", "Trường %s không tồn tại hoặc ngừng hoạt động"),
    FIELD_MUST_IS_NULL("005", "Field must is null", "Field %s must is null"),
    APPLICATION_NOT_FOUND_OR_INACTIVE("006", "Application not found or inactive", "Ứng dụng không tồn tại hặc không hoạt động"),
    ARGUMENT_NOT_VALID("007", "Method argument not valid", "Tham số không chính xác"),
    PHONE_NUMBER_ALREADY_EXIST("013", "Số điện thoại đã tồn tại"),
    OTP_NOT_COINCIDENT("014","mã OTP không trùng khớp"),
    MAX_LENGTH("016", "độ dài chuỗi không quá 255"),
    OTP_EXPIRED("015","mã OTP hết hạn"),
    EMAIL_ALREADY_EXIST("016", "Email đã tồn tại"),
    USER_NAME_ALREADY_EXIST("017", "Tên người dùng đã tồn tại"),
    COURSE_NAME_EXIST("018" , "khoá học đã tồn tại"),

    //AuthError
    AUTH_USER_PASS_INVALID("AUTH_001", "User or Password invalid", "Tài khoản hoặc mật khẩu không chính xác"),
    UPDATE_FAIL_OBJECT_ALREADY_EXIST("008", "Update Fail, Object already exist", "Cập nhật lỗi, %s đã tồn tại thông tin tương ứng"),
    CALL_INTERNAL_API_ERORR("009", "Internal Server Error", "Internal Server Error"),

    OBJECT_CAN_NOT_UPDATE("010", "Object can not update", "%s không được phép cập nhật"),
    UPLOAD_FILE_ERROR("011", "Upload file has error", "File tải lên có lỗi"),
    CUSTOM_ARGUMENT_NOT_VALID("007", "Method argument not valid", "Tham số không chính xác %s"),
    OBJECT_NOT_FOUND_OR_INACTIVE("012", "Object not found or inactive", "%s không tồn tại hặc không hoạt động"),
    DUPLICATE_PARAMS("409", "Duplicate unique params", "Tham số %s đã tồn tại trên hệ thống"),
    ;

    private final String code;
    private String desc;
    private String descFormat;

    ErrorMessage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    ErrorMessage(String code, String desc, String descFormat) {
        this.code = code;
        this.desc = desc;
        this.descFormat = descFormat;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public ErrorMessage format(Object... str) {
        if (this.descFormat != null) {
            this.desc = String.format(this.descFormat, str);
        }
        return this;
    }
}

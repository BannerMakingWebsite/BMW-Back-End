package com.example.bmw.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_NOT_FOUND(404, "이메일을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(401, "토큰을 찾을 수 없음"),
    UNAUTHORIZED_EMAIL(401, "이메일 인증이 되지 않았습니다."),
    PASSWORD_NOT_MATCH(401, "패스워드가 다릅니다."),
    CODE_NOT_MATCH(401, "인증코드가 다릅니다."),
    PASSWORD_SHORT(400, "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다."),
    CONTINUOUS_PASSWORD(400, "비밀번호에 연속된 문자열을 사용할 수 없습니다."),
    SAME_CHAR_PASSWORD(400, "비밀번호에 동일한 문자를 과도하게 연속해서 사용할 수 없습니다."),
    OUT_OF_RANGE_SPECIAL_CHAR(400, "비밀번호에 특수문자는 !@#$^*+=-만 사용 가능합니다."),
    TEMPLATE_NOT_FOUND(404, "템플릿을 찾을 수 없습니다."),
    REPORT_NOT_FOUND(404, "신고된 게시물을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(404, "카테고리를 찾을 수 없습니다."),
    DESIGN_NOT_FOUND(404, "디자인을 찾을 수 없습니다."),
    GOOD_NOT_FOUND(404, "좋아요를 누르지 않았습니다."),
    GOOD_CONFLICT(409, "이미 좋아요를 눌렀습니다."),
    TEMPLATE_EXIST(400, "템플릿이 남아있어 삭제할 수 없습니다."),
    COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다."),
    BOOKMARK_NOT_FOUND(404, "즐겨찾기를 찾을 수 없습니다.");


    private final int httpStatus;
    private final String message;
}

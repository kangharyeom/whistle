package company.whistle.global.exception;

import lombok.Getter;

@Getter
public enum Exceptions {
    ID_IS_NULL(400, "ID IS NULL"),
    IDS_ARE_NULL(400, "IDS ARE NULL"),
    NAME_IS_NULL(400, "NAME IS NULL"),
    NAMES_ARE_NULL(400, "NAMES ARE NULL"),
    IDS_OR_NAMES_ARE_NULL(400, "IDS OR NAMES ARE NULL"),
    TEAM_NAME_IS_NULL(400, "TEAM NAME IS NULL"),
    ID_NOT_EXIST(404, "ID IS NOT EXIST"),
    INVALID_DATE(400, "INVALID DATE"),
    INVALID_VALUES(400, "INVALID VALUES"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    ACCESS_FORBIDDEN(403, "ACCESS FORBIDDEN"),
    METHOD_NOT_ALLOWED(405, "METHOD NOT ALLOWED"),
    EMAIL_EXISTS(409, "EMAIL EXISTS"),
    LOGIN_ID_EXISTS(409, "LOGIN ID EXISTS"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
    NOT_IMPLEMENTATION(501, "NOT IMPLEMENTATION"),

    INVALID_EMAIL_AUTH_NUMBER(400, "INVALID EMAIL AUTHNUMBER"),
    INVALID_EMAIL_AUTH(400, "INVALID EMAIL AUTH"),
    INVALID_REFRESH_TOKEN(400, "INVALID REFRESH TOKEN"),
    EXPIRED_JWT_TOKEN(421, "EXPIRED JWT TOKEN"),
    EMAIL_AUTH_REQUIRED(403, "EMAIL AUTH REQUIRED"),

    INVALID_MEMBER_STATUS(400, "INVALID USER STATUS"),
    INVALID_PASSWORD (400, "INVALID PASSWORD"),
    PROFILE_IMAGE_NOT_FOUND(404,  "PROFILE 이미지가 업로드 되지 않았습니다."),
    MAX_FILE_SIZE_2MB(400, "MAX FILE SIZE 2MB"),

    USER_ID_IS_NULL(400, "USER ID IS NULL"),
    USER_NOT_LOGIN(400, "USER IS NOT LOGIN"),
    USER_NOT_TEAM_MANAGER(400, "USER IS NOT TEAM MANAGER"),

    USER_NOT_FOUND(404, "USER NOT FOUND"),
    USER_EXISTS(409, "USER EXISTS"),
    USER_ID_EXISTS(409, "USER_ID EXISTS"),
    USER_NOT_CREATED(403, "USER NOT CREATED"),
    USER_NOT_PATCHED(403, "USER NOT PATCHED"),
    USER_NOT_DELETED(403, "USER NOT DELETED"),

    CONTENT_ID_IS_NULL(400, "CONTENT ID IS NULL"),
    CONTENT_NOT_CREATED(403, "CONTENT NOT CREATED"),
    CONTENT_NOT_PATCHED(403, "CONTENT NOT PATCHED"),
    CONTENT_NOT_RECRUITING(403, "CONTENT STATUS NOT RECRUITING"),
    INVALID_CONTENT_STATUS(403, "INVALID CONTENT STATUS"),
    CONTENT_NOT_DELETED(404, "CONTENT NOT DELETED"),
    CONTENT_NOT_FOUND(404, "CONTENT NOT FOUND"),
    CONTENT_EXISTS(409, "CONTENT EXISTS"),
    CONTENT_CHECK_EXISTS(409, "CONTENT CHECK EXISTS"),
    CONTENT_REQUEST_EXISTS(409, "CONTENT REQUEST EXISTS"),
    CONTENT_MEMBER_EXISTS(409, "CONTENT MEMBER EXISTS"),

    COMMENT_ID_IS_NULL(400, "COMMENT ID IS NULL"),
    COMMENT_NOT_CREATED(403, "COMMENT NOT CREATED"),
    COMMENT_NOT_PATCHED(403, "COMMENT NOT PATCHED"),
    COMMENT_NOT_FOUND(404, "COMMENT NOT FOUND"),
    COMMENT_CHECK_EXISTS(409, "COMMENT CHECK EXISTS"),

    TEAM_ID_IS_NULL(400, "TEAM ID IS NULL"),
    TEAM_NOT_CREATED(403, "TEAM NOT CREATED"),
    TEAM_NOT_PATCHED(403, "TEAM NOT PATCHED"),
    TEAM_NOT_DELETED(403, "TEAM NOT DELETED"),
    TEAM_NOT_FOUND(404, "TEAM NOT FOUND"),
    TEAM_NAME_NOT_FOUND(404, "TEAM NAME NOT FOUND"),
    TEAM_MANAGER_NAME_NOT_FOUND(404, "TEAM NAME NOT FOUND"),
    TEAM_EXISTS(409, "TEAM EXISTS"),
    TEAM_ID_EXISTS(409, "TEAM_ID EXISTS"),
    TEAM_NAME_EXISTS(409, "TEAM NAME EXISTS"),
    TEAM_HAS_LEAGUE(409, "TEAM HAS LEAGUE"),
    USER_ALREADY_HAVE_TEAM(409, "USER ALREADY HAVE TEAM"),

    MATCH_ID_IS_NULL(400, "MATCH ID IS NULL"),
    MATCH_ALREADY_END(400, "MATCH ALREADY END"),
    MATCH_NOT_CREATED(403, "MATCH NOT CREATED"),
    MATCH_NOT_PATCHED(403, "MATCH NOT PATCHED"),
    MATCH_NOT_DELETED(403, "MATCH NOT DELETED"),
    MATCH_NOT_FOUND(404, "MATCH NOT FOUND"),
    MATCH_EXISTS(409, "MATCH EXISTS"),

    LEAGUE_ID_IS_NULL(400, "LEAGUE ID IS NULL"),
    LEAGUE_NOT_CREATED(403, "LEAGUE NOT CREATED"),
    LEAGUE_NOT_PATCHED(403, "LEAGUE NOT PATCHED"),
    LEAGUE_NOT_DELETED(403, "LEAGUE NOT DELETED"),
    LEAGUE_NOT_FOUND(404, "LEAGUE NOT FOUND"),
    LEAGUE_EXISTS(409, "LEAGUE EXISTS"),
    YOUR_TEAM_ALREADY_HAS_LEAGUE(409, "YOUR TEAM ALREADY HAS LEAGUE"),
    LEAGUE_NAME_EXISTS(409, "LEAGUE NAME EXISTS"),

    LEAGUE_MATCH_ID_IS_NULL(400, "LEAGUE_MATCH ID IS NULL"),
    LEAGUE_MATCH_NOT_CREATED(403, "LEAGUE_MATCH NOT CREATED"),
    LEAGUE_MATCH_NOT_PATCHED(403, "LEAGUE_MATCH NOT PATCHED"),
    LEAGUE_MATCH_NOT_DELETED(403, "LEAGUE_MATCH NOT DELETED"),
    LEAGUE_MATCH_NOT_FOUND(404, "LEAGUE_MATCH NOT FOUND"),
    LEAGUE_MATCH_EXISTS(409, "LEAGUE_MATCH EXISTS"),

    TEAM_APPLY_ID_IS_NULL(400, "TEAM_APPLY ID IS NULL"),
    TEAM_APPLY_NOT_FOUND(404, "TEAM_APPLY NOT FOUND"),
    TEAM_APPLY_NOT_CREATED(403, "TEAM_APPLY NOT CREATED"),
    TEAM_APPLY_EXISTS(409, "TEAM_APPLY EXISTS"),
    TEAM_APPLY_NOT_DELETED(403, "TEAM_APPLY NOT DELETED"),


    MATCH_IS_NOT_RECRUIT_RIVAL(400, "MATCH IS NOT RECRUIT RIVAL"),
    MATCH_APPLY_ID_IS_NULL(400, "MATCH_APPLY ID IS NULL"),
    MATCH_APPLY_NOT_FOUND(404, "MATCH_APPLY NOT FOUND"),
    MATCH_APPLY_NOT_CREATED(403, "MATCH_APPLY NOT CREATED"),
    MATCH_APPLY_NOT_DELETED(403, "MATCH_APPLY NOT DELETED"),
    MATCH_APPLY_CHECK_EXISTS(409, "MATCH_APPLY CHECK EXISTS"),
    LEAGUE_APPLY_ID_IS_NULL(400, "LEAGUE_APPLY ID IS NULL"),
    LEAGUE_APPLY_NOT_DELETED(403, "LEAGUE_APPLY NOT DELETED"),
    LEAGUE_APPLY_NOT_CREATED(403, "LEAGUE_APPLY NOT CREATED"),
    LEAGUE_APPLY_NOT_FOUND(404, "LEAGUE_APPLY NOT FOUND"),
    LEAGUE_APPLY_EXISTS(409, "LEAGUE_APPLY EXISTS"),

    PARTICIPANTS_APPLY_ID_IS_NULL(400, "PARTICIPANTS ID IS NULL"),
    PARTICIPANTS_NOT_CREATED(403, "PARTICIPANTS NOT CREATED"),
    PARTICIPANTS_NOT_PATCHED(403, "PARTICIPANTS NOT PATCHED"),
    PARTICIPANTS_NOT_DELETED(403, "PARTICIPANTS NOT DELETED"),
    PARTICIPANTS_NOT_FOUND(404, "PARTICIPANTS NOT FOUND"),
    PARTICIPANTS_EXISTS(409, "PARTICIPANTS EXISTS"),

    SQUAD_ID_IS_NULL(400, "SQUAD ID IS NULL"),
    SQUAD_NOT_CREATED(403, "SQUAD NOT CREATED"),
    SQUAD_NOT_PATCHED(403, "SQUAD NOT PATCHED"),
    SQUAD_NOT_DELETED(403, "SQUAD NOT DELETED"),
    SQUAD_NOT_FOUND(404, "SQUAD NOT FOUND"),
    SQUAD_EXISTS(409, "SQUAD EXISTS"),

    CONTENT_FILE_NOT_CREATED(403, "CONTENT_FILE NOT CREATED"),
    CONTENT_FILE_NOT_FOUND(404, "CONTENT_FILE NOT FOUND"),
    CONTENT_FILE_CHECK_ERROR(409, "CONTENT_FILE CHECK ERROR");

    private final int status;

    private final String message;

    Exceptions(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

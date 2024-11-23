package udpm.hn.metu.core.admin.user.model.response;

public interface ADUserResponse {

    String getSTT();

    String getId();

    String getName();

    String getNameBusiness();

    String getEmail();

    String getNameRole();

    String getAddress();

    String getCode();

    String getAvatarImage();

    Short getRating();

    Short getStatus();

    Boolean getIsOnline();

    Long getLastTimeActive();

}

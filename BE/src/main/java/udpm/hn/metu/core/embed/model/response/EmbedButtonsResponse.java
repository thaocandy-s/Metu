package udpm.hn.metu.core.embed.model.response;

import udpm.hn.metu.infrastructure.constant.enums.WidgetType;

import java.util.Map;

public interface EmbedButtonsResponse {

    String getId();

    String getMenuId();

    WidgetType getType();

    String getTitle();

    String getTooltip();

    String getIconUrl();

    Boolean getHighLight();

    Map<String, Object> getData();


}

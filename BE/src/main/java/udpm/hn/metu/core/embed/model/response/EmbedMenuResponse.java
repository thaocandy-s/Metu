package udpm.hn.metu.core.embed.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface EmbedMenuResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.position_class_name}")
    String getPositionClassName();

    @Value("#{target.shape_class_name}")
    String getShapeClassName();


    @Value("#{target.icon_color}")
    String getIconColor();

    @Value("#{target.text_color}")
    String getTextColor();

    @Value("#{target.background_color}")
    String getBackgroundColor();

    @Value("#{target.opacity}")
    Float getOpacity();

    @Value("#{target.appear_from}")
    Short getAppearFrom();

    @Value("#{target.appear_to}")
    Short getAppearTo();

    @Value("#{target.scroll_type}")
    Boolean getScrollType();

    @Value("#{target.scroll_value}")
    Float getScrollValue();

    @Value("#{target.time_show}")
    Float getTimeShow();

    @Value("#{target.time_hidden}")
    Float getTimeHidden();


}


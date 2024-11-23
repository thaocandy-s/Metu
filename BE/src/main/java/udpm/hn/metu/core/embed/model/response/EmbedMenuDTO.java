package udpm.hn.metu.core.embed.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmbedMenuDTO implements EmbedMenuResponse {

    private String id;
    private String code;
    private String positionClassName;
    private String shapeClassName;
    private String iconColor;
    private String textColor;
    private String backgroundColor;
    private Float opacity;
    private Short appearFrom;
    private Short appearTo;
    private Boolean scrollType;
    private Float scrollValue;
    private Float timeShow;
    private Float timeHidden;
    private List<EmbedButtonsResponse> buttons;

    private String currentUrlId;

    public EmbedMenuDTO(EmbedMenuResponse menu, List<EmbedButtonsResponse> buttons, String currentUrlId) {
        this.id = menu.getId();
        this.code = menu.getCode();
        this.positionClassName = menu.getPositionClassName();
        this.shapeClassName = menu.getShapeClassName();
        this.iconColor = menu.getIconColor();
        this.textColor = menu.getTextColor();
        this.backgroundColor = menu.getBackgroundColor();
        this.opacity = menu.getOpacity();
        this.appearFrom = menu.getAppearFrom();
        this.appearTo = menu.getAppearTo();
        this.scrollType = menu.getScrollType();
        this.scrollValue = menu.getScrollValue();
        this.timeShow = menu.getTimeShow();
        this.timeHidden = menu.getTimeHidden();
        this.buttons = buttons;
        this.currentUrlId = currentUrlId;
    }

}


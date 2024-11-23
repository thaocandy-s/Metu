package udpm.hn.metu.infrastructure.constant.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WidgetType {

    TEXT,
    SHARE,
    LINK,
    EMAIL,
    HOTLINE,
    BOOK,
    CALL_ME_BACK,
    CALL_NOW,
    LIVE_CHAT,
    SMS ;

    public static List<String> getAllWidgetType() {
        return Arrays.stream(WidgetType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static String getAllWidgetTypeString() {
        return Arrays.stream(WidgetType.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

}

package udpm.hn.metu.infrastructure.constant.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PopupSize {

    SMALL,

    LARGE;

    public static List<String> getAllDeviceType() {
        return Arrays.stream(PopupSize.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static String getAllDeviceTypeString() {
        return Arrays.stream(PopupSize.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}

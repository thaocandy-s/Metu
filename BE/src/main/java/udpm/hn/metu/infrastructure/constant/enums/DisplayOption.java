package udpm.hn.metu.infrastructure.constant.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DisplayOption {
    ALL_PATH,

    EXACT_PATH,

    CONTAIN_PATH;

    public static List<String> getAllDeviceType() {
        return Arrays.stream(DisplayOption.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static String getAllDeviceTypeString() {
        return Arrays.stream(DisplayOption.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}

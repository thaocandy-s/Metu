package udpm.hn.metu.infrastructure.constant.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    ADMIN, // setup hệ thống - value 0

    MANAGER, // chủ business - value 1

    EMPLOYEE; // nhân viên - value 2

    public static List<String> getAllRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static String getAllRolesString() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}

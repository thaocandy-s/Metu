package udpm.hn.metu.infrastructure.constant;

public final class MappingConstant {

    public static final String ADMIN = "/admin";
    public static final String MANAGER = "/manager";
    public static final String EMPLOYEE = "/employee";

    public static final String EMBED = "/embed";

    public static final String API_VERSION_PREFIX = "/api/v1";

    public static final String API_COMMON = API_VERSION_PREFIX + "/common";

    public static final String API_ADMIN_PREFIX = API_VERSION_PREFIX + ADMIN;
    public static final String API_MANAGER_PREFIX = API_VERSION_PREFIX + MANAGER;
    public static final String API_EMPLOYEE_PREFIX = API_VERSION_PREFIX + EMPLOYEE;
    public static final String API_EMBED_PREFIX = API_VERSION_PREFIX + EMBED;


    /* ADMIN */
    public static final String API_ADMIN_FEATURE = API_ADMIN_PREFIX + "/feature"; // example admin
  
    public static final String API_ADMIN_USER = API_ADMIN_PREFIX + "/user";

    public static final String API_ADMIN_SHAPE = API_ADMIN_PREFIX + "/shape"; // example admin

    public static final String API_ADMIN_POSITION = API_ADMIN_PREFIX + "/position";

    public static final String API_ADMIN_PLAN_ORDER = API_ADMIN_PREFIX + "/plan-order"; // example admin

    public static final String API_ADMIN_BUSINESS_TYPE = API_ADMIN_PREFIX + "/business-type"; // example admin

    public static final String API_ADMIN_CATEGORY = API_ADMIN_PREFIX + "/categories";

    public static final String API_ADMIN_WIDGET = API_ADMIN_PREFIX + "/widget";
    public static final String API_MANAGER_CREATE_WIDGET = API_ADMIN_PREFIX + "/create-widget";





    /* MANAGER */
    public static final String API_MANAGER_FEATURE = API_MANAGER_PREFIX + "/feature"; // example admin
  
    public static final String API_MANAGER_VIEW = API_MANAGER_PREFIX + "/View"; // example admin
  

    /* EMPLOYEE */
    public static final String API_EMPLOYEE_FEATURE = API_EMPLOYEE_PREFIX + "/feature"; // example admin


    /* AUTHENTICATION */
    public static final String API_AUTH_PREFIX = API_VERSION_PREFIX + "/auth";

    public static final String PATH_OAUTH2 = "/oauth2";


}

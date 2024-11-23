package udpm.hn.metu.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class DomainExtractorUtil {

    public static Map<String, String> getDomainAndSubdomain(String url) {
        Map<String, String> result = new HashMap<>();
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            if (host == null) {
                result.put("domain", "[NO RESULT]");
                result.put("subdomain", "");
                return result;
            }
            if (host.startsWith("www.")) {
                host = host.substring(4);
            }
            String[] parts = host.split("\\.");
            if (parts.length < 2) {
                result.put("domain", host);
                result.put("subdomain", "");
                return result;
            }
            String domain = parts[parts.length - 2] + "." + parts[parts.length - 1];
            String subdomain = String.join(".", java.util.Arrays.copyOf(parts, parts.length - 2));
            result.put("domain", domain);
            result.put("subdomain", subdomain);

            return result;

        } catch (URISyntaxException ex) {
            result.put("domain", "[NO RESULT]");
            result.put("subdomain", "");
            return result;
        }

    }

    public static Map<String, String> utmParams(String url) {
        Map<String, String> utmParams = new HashMap<>();

        if (url.contains("?")) {
            String query = url.split("\\?")[1];
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                String key = pair[0];
                String value = pair.length > 1 ? pair[1] : "N/A";
                if (key.startsWith("utm_")) {
                    utmParams.put(key, value);
                }
            }
        }
        utmParams.putIfAbsent("utm_source", "N/A");
        utmParams.putIfAbsent("utm_medium", "N/A");
        utmParams.putIfAbsent("utm_campaign", "N/A");
        utmParams.putIfAbsent("utm_content", "N/A");
        utmParams.putIfAbsent("utm_term", "N/A");

        return utmParams;
    }

}


package udpm.hn.metu.core.embed.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import udpm.hn.metu.infrastructure.constant.enums.DeviceType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmbedMenuRequest {

    public String businessCode;

    public String deviceType;

    public String currentUrl;

}

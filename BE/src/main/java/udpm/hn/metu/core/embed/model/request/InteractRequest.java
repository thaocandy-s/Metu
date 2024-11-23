package udpm.hn.metu.core.embed.model.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InteractRequest {

    public String currentUrlId;

    public String menuId;

    public String buttonId;

}

package udpm.hn.metu.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import udpm.hn.metu.infrastructure.constant.enums.WidgetType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "button")
public class Button {

    @Id
    private String id;

    @Field(targetType = FieldType.STRING)
    private String widgetId;

    @Field(targetType = FieldType.STRING)
    private String menuId;

    @Field(targetType = FieldType.STRING)
    @Enumerated(EnumType.STRING)
    private WidgetType type;

    @Field(targetType = FieldType.STRING)
    private String title;

    @Field(targetType = FieldType.STRING)
    private String tooltip;

    @Field(targetType = FieldType.STRING)
    private String iconUrl;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean highLight;

    @Field(targetType = FieldType.STRING)
    private String description;

    @Field(name = "data")
    private Map<String, Object> data = new HashMap<>();

    @JsonAnySetter
    public void addField(String key, Object value) {
        this.data.put(key, value);
    }

}

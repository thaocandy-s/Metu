package udpm.hn.metu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import udpm.hn.metu.entity.base.AuditEntity;
import udpm.hn.metu.infrastructure.constant.EntityProperties;
import udpm.hn.metu.infrastructure.constant.enums.DeviceType;
import udpm.hn.metu.infrastructure.constant.enums.DisplayOption;
import udpm.hn.metu.infrastructure.constant.enums.PopupSize;
import udpm.hn.metu.infrastructure.constant.enums.Status;

import java.io.Serializable;

@Entity
@Table(name = "popup")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Popup extends AuditEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "code", length = EntityProperties.LENGTH_CODE)
    private String code;

    @Column(name = "name", length = EntityProperties.LENGTH_NAME)
    private String name;

    @Column(name = "title", length = EntityProperties.LENGTH_NAME)
    private String title;

    @Column(name = "content", length = EntityProperties.LENGTH_CONTENT)
    private String content;

    @Column(name = "redirect_link", length = EntityProperties.LENGTH_URL)
    private String redirectLink;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "description", length = EntityProperties.LENGTH_DESCRIPTION)
    private String description;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "display_option")
    @Enumerated(EnumType.STRING)
    private DisplayOption displayOption;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;


    @Column(name = "text_color", length = EntityProperties.LENGTH_CODE)
    private String textColor;

    @Column(name = "background_color", length = EntityProperties.LENGTH_CODE)
    private String backgroundColor;

    @Column(name = "background_image", length = EntityProperties.LENGTH_URL)
    private String backgroundImage;

    @Column(name = "opacity", length = EntityProperties.LENGTH_CODE)
    private Float opacity;

    @Column(name = "popup_size")
    @Enumerated(EnumType.STRING)
    private PopupSize popupSize;


    @Column(name = "appear_from")
    private Short appearFrom;

    @Column(name = "appear_to")
    private Short appearTo;

    @Column(name = "time_show")
    private Float timeShow;

    @Column(name = "time_hidden")
    private Float timeHidden;

}

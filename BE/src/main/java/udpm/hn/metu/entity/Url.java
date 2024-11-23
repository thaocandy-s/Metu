package udpm.hn.metu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udpm.hn.metu.entity.base.SubAuditEntity;
import udpm.hn.metu.infrastructure.constant.EntityProperties;

import java.io.Serializable;

@Entity
@Table(name = "url")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Url extends SubAuditEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @Column(name = "value", length = EntityProperties.LENGTH_URL)
    private String value;

    @Column(name = "relative_value", length = EntityProperties.LENGTH_URL)
    private String relativeValue;

    @Column(name = "utm_source", length = EntityProperties.LENGTH_NAME)
    private String utmSource;

    @Column(name = "utm_medium", length = EntityProperties.LENGTH_NAME)
    private String utmMedium;

    @Column(name = "utm_campaign", length = EntityProperties.LENGTH_NAME)
    private String utmCampaign;

    @Column(name = "utm_content", length = EntityProperties.LENGTH_NAME)
    private String utmContent;

    @Column(name = "utm_term", length = EntityProperties.LENGTH_NAME)
    private String utmTerm;


}

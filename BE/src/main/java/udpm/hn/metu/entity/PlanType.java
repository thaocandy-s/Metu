package udpm.hn.metu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import udpm.hn.metu.entity.base.AuditEntity;
import udpm.hn.metu.entity.base.PrimaryEntity;
import udpm.hn.metu.infrastructure.constant.EntityProperties;
import udpm.hn.metu.infrastructure.constant.enums.Status;

import java.io.Serializable;

@Entity
@Table(name = "plan_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanType extends AuditEntity implements Serializable {

    @Column(name = "code", length = EntityProperties.LENGTH_CODE)
    private String code;

    @Column(name = "name", length = EntityProperties.LENGTH_NAME)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description", length = EntityProperties.LENGTH_DESCRIPTION)
    @Nationalized
    private String description;

    @Column(name = "number_domain")
    private Short numberDomain;

    @Column(name = "number_menu")
    private Short numberMenu;

    @Column(name = "number_staff")
    private Short numberStaff;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}

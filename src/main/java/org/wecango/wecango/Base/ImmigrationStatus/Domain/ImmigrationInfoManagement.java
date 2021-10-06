package org.wecango.wecango.Base.ImmigrationStatus.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.persistence.*;

@Entity
@Table(name = "immigrationInfoManagement")
@NoArgsConstructor
@Getter
public class ImmigrationInfoManagement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @JoinColumn(name = "nationId")
    @ManyToOne
    NationControl nationId;
    String classification;
    String contentMarkDown;
    String contentHtml;
    String updateDateTime;

}

package org.wecango.wecango.Base.ImmigrationStatus.Domain;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "immigrationInfoManagement")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ImmigrationInfoManagement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @JoinColumn(name = "nationId")
    @ManyToOne
    NationControl nationId;
    String classification;
    String contentMarkDown;
    String contentHtml;
    LocalDateTime updateDateTime;



}

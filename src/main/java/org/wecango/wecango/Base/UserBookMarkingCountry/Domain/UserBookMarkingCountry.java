package org.wecango.wecango.Base.UserBookMarkingCountry.Domain;

import lombok.*;
import org.wecango.wecango.Base.MemberManagement.Domain.MemberManagement;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "userBookMarkingCountry")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserBookMarkingCountry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @JoinColumn(name = "userUid")
    @ManyToOne
    MemberManagement userUid;
    @JoinColumn(name = "nationId")
    @ManyToOne
    NationControl nationId;
    LocalDateTime subscriptionDateTime;
    Integer orderIdx;
}

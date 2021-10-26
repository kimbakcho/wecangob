package org.wecango.wecango.Base.QABoardCategory.Domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "qaBoardCategory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QABoardCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    String categoryName;

    @Column(unique = true)
    Integer orderIdx;

}

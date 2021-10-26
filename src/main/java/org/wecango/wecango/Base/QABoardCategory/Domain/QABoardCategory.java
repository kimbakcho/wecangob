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

    @Id
    String categoryName;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer orderIdx;

}

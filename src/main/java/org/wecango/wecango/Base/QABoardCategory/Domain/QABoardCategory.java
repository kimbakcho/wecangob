package org.wecango.wecango.Base.QABoardCategory.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qaBoardCategory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QABoardCategory {

    @Id
    String categoryName;

    Integer orderIdx;

}

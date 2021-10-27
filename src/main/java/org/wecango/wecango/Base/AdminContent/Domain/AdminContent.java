package org.wecango.wecango.Base.AdminContent.Domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adminContent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AdminContent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String title;

    String markDown;

    String html;

    LocalDateTime updateTime;

    LocalDateTime createTime;

    String category;

    Integer orderIdx;
}

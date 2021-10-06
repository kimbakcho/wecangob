package org.wecango.wecango.Base.NationControl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wecango.wecango.Base.NationControl.Domain.NationControl;

public interface NationControlDataRepository extends JpaRepository<NationControl,Integer> {

}

package cn.com.cintel.validatenewskill.service.dao.generate;

import cn.com.cintel.validatenewskill.service.entity.generate.BNetPatient;
import cn.com.cintel.validatenewskill.service.entity.generate.BNetPatientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BNetPatientMapper {
    long countByExample(BNetPatientExample example);

    int deleteByExample(BNetPatientExample example);

    int deleteByPrimaryKey(String id);

    int insert(BNetPatient record);

    int insertSelective(BNetPatient record);

    List<BNetPatient> selectByExample(BNetPatientExample example);

    BNetPatient selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BNetPatient record, @Param("example") BNetPatientExample example);

    int updateByExample(@Param("record") BNetPatient record, @Param("example") BNetPatientExample example);

    int updateByPrimaryKeySelective(BNetPatient record);

    int updateByPrimaryKey(BNetPatient record);
}
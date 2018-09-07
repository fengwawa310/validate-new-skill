package cn.com.cintel.validatenewskill.service.impl;

import cn.com.cintel.validatenewskill.service.BNetPatientService;
import cn.com.cintel.validatenewskill.service.dao.generate.BNetPatientMapper;
import cn.com.cintel.validatenewskill.service.entity.generate.BNetPatient;
import cn.com.cintel.validatenewskill.service.entity.generate.BNetPatientExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;

/**
 * BNetPatientService实现
 * @author sky
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class BNetPatientServiceImpl implements BNetPatientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BNetPatientServiceImpl.class);

    @Resource
    BNetPatientMapper bNetPatientMapper;

    @Override
    public BNetPatient getBNetPatientInfo() {
        BNetPatientExample bNetPatientExample = new BNetPatientExample();
        bNetPatientExample.createCriteria().andIdEqualTo("165416F4CBDF06");
        return bNetPatientMapper.selectByExample(bNetPatientExample).get(0);
    }

    @Override
    public int testTransactional() {
        BNetPatient bNetPatient = new BNetPatient();
        bNetPatient.setId("0907");
        bNetPatient.setGender(1);
        bNetPatient.setCreateTime(new Date());
        int a = bNetPatientMapper.insert(bNetPatient);
        if(a > 0){
            throw new IllegalArgumentException("ID不符合规范");
        }
        return a;
    }


}
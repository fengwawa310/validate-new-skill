package cn.com.cintel.validatenewskill.service;

import cn.com.cintel.validatenewskill.service.entity.generate.BNetPatient;

/**
* BNetPatientService接口
*/
public interface BNetPatientService {

    BNetPatient getBNetPatientInfo();

    int testTransactional();

}
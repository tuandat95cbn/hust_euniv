/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.modules.usermanagement.service;

import java.util.List;

import vn.webapp.modules.usermanagement.model.mAcademicRank;

public interface mAcademicRankService {

    public List<mAcademicRank> list();
    public mAcademicRank loadByCode(String academicRankCode);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LoggedInUserRecordTransactionEntity;
import javax.ejb.Local;

/**
 *
 * @author weihan1394
 */
@Local
public interface LoggedInUserRecordTransactionEntityControllerLocal {

    public LoggedInUserRecordTransactionEntity createNewLoggedInUserRecordTransactionEntity(LoggedInUserRecordTransactionEntity newLoggedInUserRecordTransactionEntity);
    
}

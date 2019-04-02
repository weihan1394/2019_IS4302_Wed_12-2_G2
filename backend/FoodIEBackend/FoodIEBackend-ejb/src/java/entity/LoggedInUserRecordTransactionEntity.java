/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import util.enumeration.Transaction;

@Entity
public class LoggedInUserRecordTransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loggedInUserRecordTransactionEntityId;
    
    @Lob
    private String JWTToken;
    
    @Enumerated(EnumType.STRING)
    private Transaction transactionJob;

    @Column
    private OffsetDateTime timestampDateTime;
    
    @Column(columnDefinition = "CHAR(128)")
    private String hashedTransaction;
    
    @ManyToOne
    private LoggedInUserRecordEntity loggedInUserRecordEntity;
    
    

    public LoggedInUserRecordTransactionEntity(String JWTToken, Transaction transactionJob, String hashedTransaction, LoggedInUserRecordEntity loggedInUserRecordEntity) {
        this();
        this.JWTToken = JWTToken;
        this.transactionJob = transactionJob;
        this.hashedTransaction = hashedTransaction;
        this.loggedInUserRecordEntity = loggedInUserRecordEntity;
    }

    public LoggedInUserRecordTransactionEntity() {
        timestampDateTime = OffsetDateTime.now();
    }

    public Long getLoggedInUserRecordTransactionEntityId() {
        return loggedInUserRecordTransactionEntityId;
    }

    public void setLoggedInUserRecordTransactionEntityId(Long loggedInUserRecordTransactionEntityId) {
        this.loggedInUserRecordTransactionEntityId = loggedInUserRecordTransactionEntityId;
    }

    public String getJWTToken() {
        return JWTToken;
    }

    public void setJWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public Transaction getTransactionJob() {
        return transactionJob;
    }

    public void setTransactionJob(Transaction transactionJob) {
        this.transactionJob = transactionJob;
    }

    public OffsetDateTime getTimestampDateTime() {
        return timestampDateTime;
    }

    public void setTimestampDateTime(OffsetDateTime timestampDateTime) {
        this.timestampDateTime = timestampDateTime;
    }

    public String getHashedTransaction() {
        return hashedTransaction;
    }

    public void setHashedTransaction(String hashedTransaction) {
        this.hashedTransaction = hashedTransaction;
    }

    public LoggedInUserRecordEntity getLoggedInUserRecordEntity() {
        return loggedInUserRecordEntity;
    }

    public void setLoggedInUserRecordEntity(LoggedInUserRecordEntity loggedInUserRecordEntity) {
        this.loggedInUserRecordEntity = loggedInUserRecordEntity;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loggedInUserRecordTransactionEntityId != null ? loggedInUserRecordTransactionEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoggedInUserRecordTransactionEntity)) {
            return false;
        }
        LoggedInUserRecordTransactionEntity other = (LoggedInUserRecordTransactionEntity) object;
        if ((this.loggedInUserRecordTransactionEntityId == null && other.loggedInUserRecordTransactionEntityId != null) || (this.loggedInUserRecordTransactionEntityId != null && !this.loggedInUserRecordTransactionEntityId.equals(other.loggedInUserRecordTransactionEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LoggedInUserRecordTransactionEntity[ id=" + loggedInUserRecordTransactionEntityId + " ]";
    }
    
}

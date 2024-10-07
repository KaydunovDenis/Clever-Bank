package com.github.kaydunov.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Check {
    int number;
    Date date;
    TransactionType type;
    String recipientBankName;
    String senderBankName;
    Long accountSourceId;
    Long accountDestinationId;
    BigDecimal amount;
    //TODO: добавить валюту


    @Override
    public String toString() {
        return "Check{" +
                "number=" + number +
                ", date=" + date +
                ", type=" + type +
                ", recipientBankName='" + recipientBankName + '\'' +
                ", senderBankName='" + senderBankName + '\'' +
                ", accountSourceId=" + accountSourceId +
                ", accountDestinationId=" + accountDestinationId +
                ", amount=" + amount +
                '}';
    }
}

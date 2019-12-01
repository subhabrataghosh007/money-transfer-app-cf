package com.payment.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.payment.constant.Message;
import com.payment.constant.TransactionCode;
import com.payment.dto.MoneyTransfer;
import com.payment.model.MoneyTransferModel;
import com.payment.model.Wallet;
import com.payment.repository.Repository;
import com.payment.service.TransactionService;

@ApplicationScoped
public class TransactionServiceImpl implements TransactionService {

	@Inject
	Repository repository;
	
	public MoneyTransferModel transfer(MoneyTransfer trx) {
		
		Wallet senderWallet = repository.getById(trx.getSender());
        Wallet receiverWallet = repository.getById(trx.getReceiver());

        if (senderWallet == null || receiverWallet == null) {
        	MoneyTransferModel txnModel = createTransationModel(trx, Message.FAILED, TransactionCode.InvalidCardNumber);
        	repository.addTransaction(txnModel);
        	return txnModel;
        }

        try {
        	transferMoney(senderWallet, receiverWallet, trx.getAmount());
        } catch (Exception e) {
        	MoneyTransferModel txnModel = createTransationModel(trx, Message.FAILED, TransactionCode.UnacceptableTransactionFee);
        	repository.addTransaction(txnModel);
        	return txnModel;
		}
        
        MoneyTransferModel txnModel = createTransationModel(trx, Message.SUCCESS, TransactionCode.APPROVED);
        repository.addTransaction(txnModel);
        
        return txnModel;
    }

	public List<MoneyTransferModel> transactions() {
		return repository.allTransactions();
	}
	
	public MoneyTransferModel getByTransactionId(String id) {
		
		return repository.getTransactionsByTransactionId(id);
	}
	
	public List<MoneyTransferModel> getTransactionsBySender(String sender) {
		List<MoneyTransferModel> models = repository.getTransactionsBySender(sender);
		return models;
	}
	
	private void transferMoney(Wallet source, Wallet target, BigDecimal amount) {
		synchronized(source) {
			source.debit(amount);
		}
		synchronized(target) {
			target.credit(amount);
		}
	}
	
	private MoneyTransferModel createTransationModel(MoneyTransfer trx, Message message, TransactionCode code) {
		MoneyTransferModel txnModel = new MoneyTransferModel.MoneyTransferModelBuilder()
				.setSender(trx.getSender())
				.setReceiver(trx.getReceiver())
				.setAmount(trx.getAmount())
				.setTag(trx.getTag())
				.setStatus(message.getMessage())
				.setTransferDateTime(LocalDateTime.now().toString())
				.setDescription(code.getMessage())
				.setStatusCode(code.getCode())
				.build();
		return txnModel;
	}

	
	
	
}

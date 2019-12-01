package com.payment.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import com.payment.exception.DuplicateException;
import com.payment.exception.NotFoundException;
import com.payment.model.MoneyTransferModel;
import com.payment.model.Wallet;

import static com.payment.constant.Message.DUPLICATE_WALLET;
import static com.payment.constant.Message.WALLET_SUCCESS;
import static com.payment.constant.Message.PHONE_NUMBER_NOT_FOUND;
import static com.payment.constant.Message.TRANSACTION_NOT_FOUND;
import static com.payment.constant.Message.ADD_MONEY_SUCCESS;

@Singleton
public class Repository {

	
	private Map<String, Wallet> wallets = new ConcurrentHashMap<>();
	private List<MoneyTransferModel> transfers = new LinkedList<>();

	private Repository() {
		
	}
	
	public List<Wallet> allAccounts() {
		List<Wallet> list = wallets.entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList());
		return list;
	}

	public Wallet addAccount(Wallet wallet) {
		if (wallets.containsKey(wallet.getPhoneNumber())) {
			throw new DuplicateException(DUPLICATE_WALLET.getMessage());
		}
		
		wallet.setRespnseMessage(WALLET_SUCCESS.getMessage());
		wallets.put(wallet.getPhoneNumber(), wallet);
		return wallet;
	}

	public Wallet addMoney(Wallet wallet) {

		Wallet storedWallet = Optional.ofNullable(wallets.get(wallet.getPhoneNumber()))
				.orElseThrow(() -> new NotFoundException(PHONE_NUMBER_NOT_FOUND.getMessage()));
		
		wallet.setBalance(storedWallet.getBalance().add(wallet.getBalance()));
		wallets.put(wallet.getPhoneNumber(), wallet);
		wallet.setRespnseMessage(wallet.getBalance().toString()+ADD_MONEY_SUCCESS.getMessage()+wallet.getPhoneNumber());
		
		return wallet;
	}

	public Wallet getById(String source) {
		return wallets.get(source);
	}

	public void addTransaction(MoneyTransferModel txn) {
		transfers.add(txn);
	}

	public List<MoneyTransferModel> allTransactions() {
		List<MoneyTransferModel> list = transfers.stream().collect(Collectors.toList());
		return list;
	}
	
	public MoneyTransferModel getTransactionsByTransactionId(String id) {
		
		MoneyTransferModel moneyTransferModel = transfers.stream().filter(p -> p.getTransactionId().toString().equals(id))
				.findFirst().orElseThrow(() -> new NotFoundException(TRANSACTION_NOT_FOUND+id));
		
		return moneyTransferModel;
	}
	
	public List<MoneyTransferModel> getTransactionsBySender(String sender) {
		List<MoneyTransferModel> models = transfers.stream().filter(p -> p.getSender().equals(sender)).collect(Collectors.toList());
		return models;
	}
	
}

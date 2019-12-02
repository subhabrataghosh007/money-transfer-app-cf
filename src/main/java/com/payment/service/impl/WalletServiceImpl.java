package com.payment.service.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.payment.model.Wallet;
import com.payment.repository.Repository;
import com.payment.service.WalletService;

@ApplicationScoped
public class WalletServiceImpl implements WalletService {

	@Inject
	Repository fundRepository;

	public List<Wallet> allWallet() {

		List<Wallet> accounts = fundRepository.allAccounts();
		return accounts;
	}

	public Wallet addWallet(Wallet account) {

		return fundRepository.addAccount(account);
	}

	public Wallet addMoney(Wallet account, String phoneNumber) {
		account = fundRepository.addMoney(account, phoneNumber);
		return account;
	}
}

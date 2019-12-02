package com.payment.service;

import java.util.List;

import com.payment.model.Wallet;

public interface WalletService {

	public List<Wallet> allWallet();

	public Wallet addWallet(Wallet account);

	public Wallet addMoney(Wallet account, String phoneNumber);

}

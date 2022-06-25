package com.casino.dagacube.bootstrap;


import com.casino.dagacube.model.Player;
import com.casino.dagacube.model.Transaction;
import com.casino.dagacube.model.TransactionType;
import com.casino.dagacube.repository.PlayerRepository;
import com.casino.dagacube.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Loader implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    public Loader(PlayerRepository playerRepository,TransactionRepository transactionRepository){
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        loadDetails();
    }

    private void loadDetails() {

        if (playerRepository.count() == 0) {

            Double value =0d;
            Player player = new Player();
            player.setPlayerId(1);
            player.setUserName("Sethu");
            player.setBalance(10d);

            playerRepository.save(player);

            Transaction transaction = new Transaction();
            transaction.setAmount(10d);
            transaction.setPlayerId(player);
            transaction.setTransactionType(TransactionType.WIN);

            if(transaction.getTransactionType() == TransactionType.WAGER){
                value = transaction.getAmount() - player.getBalance();
            } else if(transaction.getTransactionType() == TransactionType.WIN){
                value = transaction.getAmount() + player.getBalance();
            }

            player.setBalance(value);
            playerRepository.save(player);
        }


    }
}

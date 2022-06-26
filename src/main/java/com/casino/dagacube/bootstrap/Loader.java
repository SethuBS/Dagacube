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
            player.setUserName("Sethu");
            player.setBalance(2500d);

            playerRepository.save(player);

            Transaction transaction1 = new Transaction();

            transaction1.setAmount(5000d);
            transaction1.setPlayerId(player);
            transaction1.setTransactionType(TransactionType.WIN);

            if(transaction1.getTransactionType() == TransactionType.WAGER){
                if(value < player.getBalance()){
                    value = 0d;
                } else {
                    value = player.getBalance() - transaction1.getAmount();
                }
            } else if(transaction1.getTransactionType() == TransactionType.WIN){
                value = player.getBalance() + transaction1.getAmount();
            }

            player.setBalance(value);
            playerRepository.save(player);
            transactionRepository.save(transaction1);
        }


    }
}

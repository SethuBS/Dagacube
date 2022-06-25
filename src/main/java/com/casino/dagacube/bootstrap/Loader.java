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
            player.setBalance(0d);

            playerRepository.save(player);

            Transaction transaction1 = new Transaction();


            transaction1.setTransactionId(1);
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

            transactionRepository.save(transaction1);
            player.setBalance(value);
            playerRepository.save(player);

            Transaction transaction2 = new Transaction();
            transaction2.setTransactionId(1);
            transaction2.setAmount(300d);
            transaction2.setPlayerId(player);
            transaction2.setTransactionType(TransactionType.WAGER);

            if(transaction2.getTransactionType() == TransactionType.WAGER){
                if(value < player.getBalance()){
                    value = 0d;
                } else {
                    value =  player.getBalance() - transaction2.getAmount();
                }
            } else if(transaction2.getTransactionType() == TransactionType.WIN){
                value =  player.getBalance() + transaction2.getAmount();
            }

            transactionRepository.save(transaction2);
            player.setBalance(value);
            playerRepository.save(player);
        }


    }
}

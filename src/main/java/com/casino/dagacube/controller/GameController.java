package com.casino.dagacube.controller;


import com.casino.dagacube.model.Player;
import com.casino.dagacube.model.Transaction;
import com.casino.dagacube.model.TransactionType;
import com.casino.dagacube.repository.PlayerRepository;
import com.casino.dagacube.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/casino")
public class GameController {

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("player/{id}")
    public ResponseEntity<Player> getBalance(@PathVariable Integer id){

        Optional<Player> player = playerRepository.findById(id);

        Map<String, Object> objectMap = new HashMap<>();

        if(player.isPresent()){
            objectMap.put("balance", player.get().getBalance());
            objectMap.put("playerId", player.get().getPlayerId());
            return new ResponseEntity(objectMap, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    @PostMapping("player/{playerId}/{amount}/{type}")
    public ResponseEntity update(@PathVariable Integer playerId, @PathVariable Double amount, @PathVariable TransactionType type) {

        Optional<Player> player = playerRepository.findById(playerId);

        Double value =0d;

        Map<String, Object> objectMap = new HashMap<>();

        if(player.isPresent()){

            if(amount > 0){
                if(type == TransactionType.WAGER){

                    if(player.get().getBalance() < amount){
                        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
                    } else {
                        value = player.get().getBalance() - amount;
                    }

                } else if(type == TransactionType.WIN){
                    value = player.get().getBalance() + amount;
                }

                player.get().setBalance(value);
                playerRepository.save(player.get());

                Transaction transaction = new Transaction();
                transaction.setPlayerId(player.get());
                transaction.setTransactionType(type);
                transaction.setAmount(amount);
                transactionRepository.save(transaction);

                Optional<Transaction> transaction1 = transactionRepository.findById(transaction.getTransactionId());

                objectMap.put("transactionId",transaction1.get().getTransactionId());
                objectMap.put("balance", player.get().getBalance());

                return new ResponseEntity(objectMap, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    @PostMapping("admin/player/transactions/{userName}")
    public ResponseEntity admin(@PathVariable String userName){


        List<Player> players = (List<Player>) playerRepository.findAll();


        List<Transaction> transactionList = (List<Transaction>) transactionRepository.findAll();


        Map<String, Object> objectMap = new HashMap<>();

        List<Transaction> transactions = new ArrayList<>();

        int count = 1;

        for (Player playerInstance: players){

            if(playerInstance.getUserName().equals(userName)){
               Player player = playerInstance;
                for(Transaction transaction: transactionList){
                    if(player.equals(transaction.getPlayerId())){
                        transactions.add(transaction);
                        if(count >=10){
                            break;
                        }
                        count++;
                    }
                }

                Collections.reverse(transactions);

                objectMap.put("json_data", transactions);

                return new ResponseEntity(objectMap, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }
}

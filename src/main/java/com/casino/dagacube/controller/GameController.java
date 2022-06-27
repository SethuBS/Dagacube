package com.casino.dagacube.controller;

import com.casino.dagacube.model.Player;
import com.casino.dagacube.model.Transaction;
import com.casino.dagacube.model.TransactionType;
import com.casino.dagacube.repository.CustomRepository;
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
    @Autowired
    CustomRepository customRepository;

    @GetMapping("player/{id}")
    public ResponseEntity<Player> getBalance(@PathVariable java.lang.Integer id){

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
    public ResponseEntity update(@PathVariable java.lang.Integer playerId, @PathVariable Double amount, @PathVariable TransactionType type) {

        Optional<Player> player = playerRepository.findById(playerId);

        Double value =0d;

        Map<String, Object> objectMap = new HashMap<>();

        if(player.isPresent()){

            if(amount > 0){
                if(type == TransactionType.WAGER){

                    if(player.get().getBalance() < amount){
                        return new ResponseEntity<>( HttpStatus.I_AM_A_TEAPOT);
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

                Optional<Transaction> transactionInstance = transactionRepository.findById(transaction.getTransactionId());

                objectMap.put("transactionId",transactionInstance.get().getTransactionId());
                objectMap.put("balance", player.get().getBalance());

                return new ResponseEntity(objectMap, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    @PostMapping("admin/player/transactions/{userName}")
    public ResponseEntity admin(@PathVariable String userName){

        List<Player> players = (List<Player>) playerRepository.findAll();

        List<Transaction> transactionList = customRepository.findTopTen(10);

        for (Player playerInstance: players){

            Map<String, Object> objectMap = new HashMap<>();

            List<Object> transactions = new ArrayList<>();
            HashMap hashMap;

            if(playerInstance.getUserName().equals(userName)){
               Player player = playerInstance;
                for(Transaction transaction: transactionList){
                    hashMap = new HashMap();
                    if(player.equals(transaction.getPlayerId())){
                        hashMap.put("transactionType",transaction.getTransactionType());
                        hashMap.put("transactionId",transaction.getTransactionId());
                        hashMap.put("amount",transaction.getAmount());
                        transactions.add(hashMap);
                    }
                }

                objectMap.put("transactions_array", transactions);

                return new ResponseEntity(objectMap, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }
}

package it.polimi.ingsw.server.viewer_server;

import it.polimi.ingsw.clients.view_client.CLI;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model_server.board.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RemoteView {
    CLI cli;

    public RemoteView() {
        cli = new CLI();
    }

    public void scegliLeCarteLeaderAllInizio(CardLeader[] cards) {
        ArrayList<CardLeader> list = new ArrayList<>();
        var finale = new CardLeader[2];
        Collections.addAll(list, cards);
        cli.showCards(list);
        System.out.println("Indica il codice (le ultime due cifre del nome) delle carte scelte");
        var input = new Scanner(System.in);
        String risposta;
        risposta = input.nextLine();
        var i = 0;
        while (i<2)
            if (Integer.parseInt(risposta) <= 0 || Integer.parseInt(risposta) >= 16) {
                System.out.println("Errore");
            } else {
                System.out.println("Hai scelto " + risposta + ", giusto?");
                finale[i] = CardLeader.getSpecificCard(Integer.parseInt(risposta));
                i++;
            }
        //bc.pickCardLeader(finale);
    }

}

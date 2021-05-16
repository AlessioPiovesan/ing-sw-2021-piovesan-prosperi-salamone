package it.polimi.ingsw.server.viewer_server;

import it.polimi.ingsw.clients.view_client.CLI;
import it.polimi.ingsw.clients.view_client.UI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller_server.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RemoteView {
    UI ui;
    Controller contoller;

    public RemoteView() {
        ui = new CLI();
    }

    public void scegliLeCarteLeaderAllInizioDomanda(CardLeader[] cards) {
        ArrayList<CardLeader> list = new ArrayList<>();
        Collections.addAll(list, cards);
        ui.showCards(list);
        System.out.println("Indica il codice (le ultime due cifre del nome) delle carte scelte");
        scegliLeCarteLeaderAllInizioRisposta(0);
    }

    public void scegliLeCarteLeaderAllInizioRisposta(int id) {
        var finale = new CardLeader[2];
        var input = new Scanner(System.in);
        String risposta = input.nextLine();
        var i = 0;
        while (i < 2)
            if (Integer.parseInt(risposta) <= 0 || Integer.parseInt(risposta) >= 16) {
                System.out.println("Errore");
            } else {
                System.out.println("Hai scelto " + risposta + ", giusto?");
                finale[i] = CardLeader.getSpecificCard(Integer.parseInt(risposta));
                i++;
            }
        contoller.getGameControllerById(id).getActiveBoardController().pickCardLeader(finale);
    }

    public void getRisorsaPreferita() {
        System.out.println("Scegli una risorsa tra:");
        var i = 0;
        for (Colours res : Colours.valid()) {
            System.out.println(i + ") " + res.getType());
            i++;
        }
        var input = new Scanner(System.in);
        var k = Integer.parseInt(input.nextLine());
        var risposta = Colours.valid().get(k);
        System.out.println("Su quale ripiano? (1, 2, 3)");
        var j = Integer.parseInt(input.nextLine());
        mettiRisorseNelMagazzino(0, risposta, j);

    }

    public void mettiRisorseNelMagazzino(int id, Colours colour, int lvl) {
        contoller.getGameControllerById(id).getActiveBoardController().warehouseResHandler(colour, 1);
    }

    public void mettiRisorseNelDeposito(int id, Colours colour, int lvl) {
        contoller.getGameControllerById(id).getActiveBoardController().strongboxResHandler(colour, 1);
    }

    public void nextChiedi() {
        System.out.println("""
                Prossima azione?
                1) Attivare la produzione in plancia
                2) Prendere risorse dal mercato
                3) Comprare una carta sviluppo
                4) Attivare Carta Leader
                """);
        nextRisposta(0);
    }

    public void nextRisposta(int id) {
        var input = new Scanner(System.in);
        var risposta = Integer.parseInt(input.nextLine());
        switch (risposta) {
            case 1: {
                contoller.getGameControllerById(id).prodAction();
            }
            case 2: {
                System.out.println("Mostro il mercato");
                /*Mostro il mercato*/
                System.out.println("Chiedo la mossa in maniera furba chiedendo riga/colonna");
                var j = Integer.parseInt(input.nextLine());
                contoller.getGameControllerById(id).marketAction(j);
            }
            case 3: {
                System.out.println("Mostro tutte le carte comprabili");
                /*Mostro le carte*/
                System.out.println("Chiedo di decidere");
                var j = input.nextLine();
                for (CardDev cardDev : CardDev.values())
                    if (j.equals(cardDev.name()))
                        contoller.getGameControllerById(id).cardDevAction(cardDev);

            }
            case 4: {
                System.out.println("Mostro tutte le carte comprabili");
                /*Mostro le carte*/
                System.out.println("Chiedo di decidere");
                var j = input.nextLine();
                for (CardLeader cardLeader : CardLeader.values())
                    if (j.equals(cardLeader.name()))
                        contoller.getGameControllerById(id).cardLeadAction(cardLeader);
            }
            default: {
                System.out.println("ERRORE");
            }
        }
    }
}

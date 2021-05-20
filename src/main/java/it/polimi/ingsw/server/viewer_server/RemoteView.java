package it.polimi.ingsw.server.viewer_server;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model_server.board.Board;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class RemoteView implements PropertyChangeListener {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Board board;
    private boolean ready = false;


    public RemoteView(Board board) {
        board.addPropertyChangeListener(this);
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
        mettiRisorseNelMagazzino(0, risposta, 1, j);

    }

    public void mettiRisorseNelMagazzino(int id, Colours colour, int qnt, int lvl) {
        //controller.getGameControllerById(id).getActiveBoardController().warehouseResHandler(colour, qnt, lvl);
    }

    public void mettiRisorseNelDeposito(int id, Colours colour, int qnt) {
        //controller.getGameControllerById(id).getActiveBoardController().strongboxResHandler(colour, qnt);
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
            case 1 -> {
                //controller.getGameControllerById(id).prodAction();
            }
            case 2 -> {
                System.out.println("Mostro il mercato");
                /*Mostro il mercato*/
                System.out.println("Chiedo la mossa in maniera furba chiedendo riga/colonna");
                var j = Integer.parseInt(input.nextLine());
                //controller.getGameControllerById(id).marketAction(j);
            }
            case 3 -> {
                System.out.println("Mostro tutte le carte comprabili");
                /*Mostro le carte*/
                System.out.println("Chiedo di decidere (col numero se le numeriamo, col nome se è riconoscibile)");
                var j = input.nextLine();
                // for (CardDev cardDev : CardDev.values())
                // if (j.equals(cardDev.name()))
                //controller.getGameControllerById(id).cardDevAction(cardDev);
            }
            case 4 -> {
                System.out.println("Mostro tutte le carte attivabili");
                /*Mostro le carte*/
                System.out.println("Chiedo di decidere (col numero se le numeriamo, col nome se è riconoscibile)");
                var j = input.nextLine();
                //for (CardLeader cardLeader : controller.getGameControllerById(id).getGame().getSingleB(controller.getGameControllerById(id).getGame().getPlayingPlayer()).getMyLeaderCards())
                //    if (j.equals(cardLeader.name()))
                //        controller.getGameControllerById(id).cardLeadAction(cardLeader);
            }
            default -> {
                System.out.println("ERRORE");
            }
        }
    }

    public void resourcesHandler(LinkedList<Pick> bufferProd) {
        var input = new Scanner(System.in);
        System.out.println("Da dove le prendo?");
        for (Pick pick : bufferProd) {
            System.out.println(pick);
            System.out.println("1)Magazzino o 2)Forziere?");
            var j = Integer.parseInt(input.nextLine());
            switch (j) {
                case 1 -> mettiRisorseNelMagazzino(0, pick.getResource(), -pick.getQnt(), 1);
                case 2 -> mettiRisorseNelDeposito(0, pick.getResource(), -pick.getQnt());
                default -> System.out.println("Errore");
            }

        }
    }

    public void resourcesHandlerMarket(LinkedList<Colours> bufferMarket) {
        var input = new Scanner(System.in);
        var keep = new ArrayList<Colours>();
        var loose = new ArrayList<Colours>();
        var lvls = new ArrayList<Integer>();
        System.out.println("Mostro le risorse");
        /*Mostro le risorse*/
        for (Colours colours : bufferMarket) {
            System.out.println(colours + "la metto in magazzino?");
            var j = Integer.parseInt(input.nextLine());
            if (j == 0) loose.add(colours);
            if (j == 1) {
                keep.add(colours);
                System.out.println("A che livello del magazzino?");
                lvls.add(Integer.parseInt(input.nextLine()));
            }
        }
        //controller.getGameControllerById(0).addFaithPnt(loose);
        for (Colours colours : keep)
            mettiRisorseNelMagazzino(0, colours, 1, lvls.get(keep.indexOf(colours)));
    }

    public void setMyLeaderCards(CardLeader[] myLeaderCards) {
        pcs.firePropertyChange("myLeaderCards", "", myLeaderCards);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case "active": {
                if (!ready) {
                    ready = !ready;
                    /*deve distribuire le risorse*/
                }
            }
        }

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}

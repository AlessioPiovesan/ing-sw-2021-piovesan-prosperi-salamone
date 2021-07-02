package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record Utils(UI ui){

    /**
     * Method castList cast a rawCollection in a list of objects of a certain class
     *
     * @param clazz         a certain class to assign to the object of the future list
     * @param rawCollection a list of objects
     * @param <T>           generification
     * @return a list of objects of class clazz
     */
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection){
        List<T> result = new ArrayList<>(rawCollection.size());
        for(Object o : rawCollection){
            try{
                result.add(clazz.cast(o));
            } catch(ClassCastException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getPath(CardLeader card){
        return "/graphics/cards/front/lead/" + card.name() + ".png";
    }
}

package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.clients.viewer.UI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils{

    private final UI ui;

    public Utils(UI ui){
        this.ui = ui;
    }

    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection){
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
}

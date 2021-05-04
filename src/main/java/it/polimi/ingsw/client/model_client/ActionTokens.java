package it.polimi.ingsw.client.model_client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ActionTokens {
    AT1, AT2, AT3, AT4, AT5, AT6, AT7;

    private final Random random = new Random();

    public List<ActionTokens> shuffle() {
        ArrayList<ActionTokens> stack = new ArrayList<>();
        ArrayList<ActionTokens> newStack = new ArrayList<>();
        Collections.addAll(stack, ActionTokens.values());
        while (stack.size() != newStack.size()) {
            var i = random.nextInt(stack.size());
            if (!(newStack.contains(stack.get(i)))) {
                newStack.add(stack.get(i));
            }
        }
        return newStack;
    }

    public void newAction(List<ActionTokens> stack) {
        ActionTokens a = stack.get(0);
        stack.remove(0);
        stack.add(a);
    }
}

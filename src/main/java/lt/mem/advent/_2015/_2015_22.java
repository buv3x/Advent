package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class _2015_22 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/22.txt");
//        first(lineInput);
        second(lineInput);
    }

    private static int minManaUsed = Integer.MAX_VALUE;

    public static void first(List<String> lineInput) {
        int bossHealth = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(0), ": "));
        int bossDamage = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(1), ": "));
        State state = new State();
        state.bossDamage = bossDamage;
        state.bossHealth = bossHealth;

        chooseAction(state);

        System.out.println(minManaUsed);
    }


    public static void second(List<String> lineInput) {
        int bossHealth = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(0), ": "));
        int bossDamage = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(1), ": "));
        State state = new State();
        state.bossDamage = bossDamage;
        state.bossHealth = bossHealth;
        state.healthDrop = true;

        chooseAction(state);

        System.out.println(minManaUsed);
    }

    private static void chooseAction(State state) {
        if(state.usedMana >= minManaUsed) {
            return;
        }
        if(state.mana < Action.MISSILE.cost) {
            return;
        }

        if(state.bossHealth <= 4) {
            processAction(state, Action.MISSILE);
        } else {
            LinkedList<Action> actions = new LinkedList<>();
            actions.add(Action.MISSILE);
            if(state.mana >= Action.DRAIN.cost) {
                actions.add(Action.DRAIN);
            }
            if(state.remainingCharge == 0 && state.mana >= Action.CHARGE.cost) {
                actions.add(Action.CHARGE);
            }
            if(state.remainingPoison == 0 && state.mana >= Action.POISON.cost) {
                if(state.mana >= Action.SHIELD.cost + Action.POISON.cost) {
                    actions.add(Action.POISON);
                } else {
                    actions.addFirst(Action.POISON);
                }
            }
            if(state.remainingShield == 0 && state.mana >= Action.SHIELD.cost) {
                if(state.mana >= Action.SHIELD.cost + Action.CHARGE.cost) {
                    actions.add(Action.SHIELD);
                } else {
                    actions.addFirst(Action.SHIELD);
                }
            }


            for (Action action : actions) {
                processAction(state, action);
            }
        }


    }

    private static void processAction(State oldState, Action action) {
        State state = new State(oldState);
        state.usedActions.add(action);
        state.mana = state.mana - action.cost;
        state.usedMana = state.usedMana + action.cost;
        switch (action) {
            case MISSILE: {
                state.bossHealth -= 4;
                break;
            }
            case DRAIN: {
                state.bossHealth -= 2;
                state.health += 2;
                break;
            }
            case SHIELD: {
                state.remainingShield = 6;
                break;
            }
            case POISON: {
                state.remainingPoison = 6;
                break;
            }
            case CHARGE: {
                state.remainingCharge = 5;
                break;
            }
        }
        if(state.bossHealth <= 0) {
            endGame(state);
            return;
        }

        // Boss Turn
        if(state.remainingShield > 0) {
            state.remainingShield--;
        }
        if(state.remainingCharge > 0) {
            state.remainingCharge--;
            state.mana += 101;
        }
        if(state.remainingPoison > 0) {
            state.remainingPoison--;
            state.bossHealth -= 3;
            if(state.bossHealth <= 0) {
                endGame(state);
                return;
            }
        }
        state.health -= (state.remainingShield > 0) ? Math.max(1, state.bossDamage - 7) : state.bossDamage;
        if(state.health <= 0) {
            return;
        }

        // Players Turn
        if(state.healthDrop) {
            state.health--;
            if(state.health <= 0) {
                return;
            }
        }
        if(state.remainingShield > 0) {
            state.remainingShield--;
        }
        if(state.remainingCharge > 0) {
            state.remainingCharge--;
            state.mana += 101;
        }
        if(state.remainingPoison > 0) {
            state.remainingPoison--;
            state.bossHealth -= 3;
            if(state.bossHealth <= 0) {
                endGame(state);
                return;
            }
        }

        chooseAction(state);
    }

    private static void endGame(State state) {
        if(state.usedMana < minManaUsed) {
            minManaUsed = state.usedMana;
            System.out.println(minManaUsed + ":  " + state.usedActions);
        }
    }


    private static class State {
        int bossHealth;
        int bossDamage;
        int health = 50;
        int mana = 500;
        int usedMana = 0;
        int remainingShield = 0;
        int remainingPoison = 0;
        int remainingCharge = 0;
        boolean healthDrop = false;
        List<Action> usedActions = new ArrayList<>();

        public State() {
        }

        public State(State state) {
            this.bossHealth = state.bossHealth;
            this.bossDamage = state.bossDamage;
            this.health = state.health;
            this.mana = state.mana;
            this.usedMana = state.usedMana;
            this.remainingShield = state.remainingShield;
            this.remainingPoison = state.remainingPoison;
            this.remainingCharge = state.remainingCharge;
            this.usedActions = new ArrayList<>(state.usedActions);
            this.healthDrop = state.healthDrop;
        }
    }


    private enum Action {
        MISSILE(53),
        DRAIN(73),
        SHIELD(113),
        POISON(173),
        CHARGE(229);

        Action(int cost) {
            this.cost = cost;
        }

        final int cost;

    }

}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import javafx.util.Pair;

public class MU {

    static int counter = 1;
    static ArrayList<Pair<Integer, String>> results;

    private static boolean ruleI(Pair<Integer, String> theorem, Queue<Pair<Integer, String>> q, String toProve) {
        if (theorem.getValue().charAt(theorem.getValue().length() - 1) == 'I') {
            String res = theorem.getValue() + 'U';
            System.out.println(counter + ". " + res + " desde el teorema " + theorem.getKey() + " por la regla I");
            results.add(new Pair(theorem.getKey(), res));
            if (res.compareTo(toProve) == 0) {
                return true;
            }
            q.add(new Pair(counter++, res));
        }
        return false;
    }

    private static boolean ruleII(Pair<Integer, String> theorem, Queue<Pair<Integer, String>> q, String toProve) {
        if (theorem.getValue().charAt(0) == 'M') {
            String res = theorem.getValue().substring(1);
            res = theorem.getValue() + res;
            System.out.println(counter + ". " + res + " desde el teorema " + theorem.getKey() + " por la regla II");
            results.add(new Pair(theorem.getKey(), res));
            if (res.compareTo(toProve) == 0) {
                return true;
            }
            q.add(new Pair(counter++, res));
        }
        return false;
    }

    private static boolean ruleIII(Pair<Integer, String> theorem, Queue<Pair<Integer, String>> q, String toProve) {
        if (theorem.getValue().contains("III")) {
            String res = theorem.getValue().replaceFirst("III", "U");
            System.out.println(counter + ". " + res + " desde el teorema " + theorem.getKey() + " por la regla III");
            results.add(new Pair(theorem.getKey(), res));
            if (res.compareTo(toProve) == 0) {
                return true;
            }
            q.add(new Pair(counter++, res));
        }
        return false;
    }

    private static boolean ruleIV(Pair<Integer, String> theorem, Queue<Pair<Integer, String>> q, String toProve) {
        if (theorem.getValue().contains("UU")) {
            String res = theorem.getValue().replaceFirst("UU", "");
            System.out.println(counter + ". " + res + " desde el teorema " + theorem.getKey() + " por la regla IV");
            results.add(new Pair(theorem.getKey(), res));
            if (res.compareTo(toProve) == 0) {
                return true;
            }
            q.add(new Pair(counter++, res));
        }
        return false;
    }

    public static void printResult(int i) {
        if (i == 0) {
            System.out.println(results.get(i).getValue());
            return;
        }
        printResult((results.get(i).getKey()) - 1);
        System.out.println("=> " + results.get(i).getValue());
    }

    public static boolean mu(String axiom, String toProve, int max) {
        //Recorrido en anchura.
        results = new ArrayList<>(max);
        Queue<Pair<Integer, String>> q = new LinkedList<>();
        System.out.println(counter + ". " + axiom + " axioma");
        results.add(new Pair(0, axiom));
        q.add(new Pair(counter++, axiom));
        Pair theorem;
        while (!q.isEmpty() && counter <= max) {
            theorem = q.remove();
            if (ruleI(theorem, q, toProve)) {
                System.out.println("Por lo tanto " + toProve + " es un teorema con la siguiente secuencia:");
                printResult(results.size() - 1);
                return true;
            }
            if (counter <= max && ruleII(theorem, q, toProve)) {
                System.out.println("Por lo tanto " + toProve + " es un teorema con la siguiente secuencia:");
                printResult(results.size() - 1);
                return true;
            }
            if (counter <= max && ruleIII(theorem, q, toProve)) {
                System.out.println("Por lo tanto " + toProve + " es un teorema con la siguiente secuencia:");
                printResult(results.size() - 1);
                return true;
            }
            if (counter <= max && ruleIV(theorem, q, toProve)) {
                System.out.println("Por lo tanto " + toProve + " es un teorema con la siguiente secuencia:");
                printResult(results.size() - 1);
                return true;
            }
        }
        System.out.println("No fue posible probar el teorema " + toProve + " con el axioma " + axiom + " en menos de " + max + " intentos");
        return false;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Inserte el teorema a probar");
        String toProve = scan.next();
        System.out.println("Inserte el número máximo de teoremas a desarrollar");
        int max = scan.nextInt();
        mu("MI", toProve, max);
    }
}

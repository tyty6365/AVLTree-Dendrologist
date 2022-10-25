package dendrologist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * A testbed for an augmented implementation of an AVL tree
 *
 * @author William Duncan, Tyler Scott
 * @see AVLTreeAPI  <pre>
 * Date: 10/18/2022
 * Course: CSC 3102
 * Programming Project # 2
 * Instructor: Dr. Duncan
 * </pre>
 */
public class Dendrologist {

    public static void main(String[] args) throws FileNotFoundException {
        String usage = "Dendrologist <order-code> <command-file>\n";
        usage += "  <order-code>:\n";
        usage += "  0 ordered by increasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  -1 for reverse lexicographical order\n";
        usage += "  1 for lexicographical order\n";
        usage += "  -2 ordered by decreasing string\n";
        usage += "  2 ordered by increasing string\n";
        usage += "  -3 ordered by decreasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  3 ordered by increasing string length, primary key, and lexicographical order, secondary key\n";
        if (args.length != 2) {
            System.out.println(usage);
            throw new IllegalArgumentException("There should be 2 command line arguments.");
        }
        
    
        File stringFile = new File(args[1]);
        Scanner inFile = new Scanner(stringFile);
        Function<String, PrintStream> func = x -> {
            return System.out.printf("%s%n", x);
        };

        int nodes = Integer.valueOf(args[0]);

        Comparator<String> fn = (a1, a2)
                -> {
            switch (nodes) {
                case 0 -> {
                    if (a1.length() != a2.length()) {
                        return a1.length() - a2.length();
                    } else {
                        return -(a1.compareTo(a2));
                    }
                }
                case (-1) -> {
                    return -(a1.compareTo(a2));
                }
                case (1) -> {
                    return a1.compareTo(a2);
                }
                case (-2) -> {
                    return -(a1.length() - a2.length());
                }
                case (2) -> {
                    return a1.length() - a2.length();
                }
                case (-3) -> {
                    if (a1.length() != a2.length()) {
                        return -(a1.length() - a2.length());
                    } else {
                        return -(a1.compareTo(a2));
                    }
                }
                case (3) -> {
                    if (a1.length() != a2.length()) {
                        return a1.length() - a2.length();
                    } else {
                        return a1.compareTo(a2);
                    }
                }
                default -> {
                    System.out.println("The order code is between and including -3 and 3.");
                    return 0;
                }
            }

        };

        AVLTree avl = new AVLTree(fn);
        String print1 = "";
        String print2 = "";
        while (inFile.hasNextLine()) {
            if (inFile.hasNext()) {
                print1 = inFile.next();
            }
            if (print1.equals("stats")) {
                System.out.println("Stats: size = " + avl.size() + ", height = " + avl.height() + ", #full-nodes = " + avl.fullCount() + ", fibonacci? = " + avl.isFibonacci());
                print1 = "";
            }
            if (print1.equals("insert")) {
                if (inFile.hasNextLine()) {
                    print2 = inFile.next();
                }
                avl.insert(print2);
                System.out.println("Inserted:" + print2);
                print2 = "";
                print1 = "";
            }
            if (print1.equals("paths")) {
                ArrayList<String> list = avl.genPaths();
                System.out.println("#Root-To-Leaf-Paths:" + list.size());
                for (int i =0; i <list.size(); i++ ){
                    System.out.println(list.get(i));
                }
                print1 = "";
            }
            if (print1.equals("delete")) {
                if (inFile.hasNextLine()) {
                    print2 = inFile.next();
                }
                avl.remove(print2);
                System.out.println("Deleted: " + print2);
                print2 = "";
                print1 = "";
            }
            if (print1.equals("traverse")) {

                System.out.println("In-Order Traversal: ");
                avl.traverse(func);
                print1 = "";
            }
             
        }
    }
}

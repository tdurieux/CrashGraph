package github.tdurieux.crashGraph.main;

import java.io.IOException;
import java.util.Set;

import github.tdurieux.crashGraph.entities.Bucket;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out
                    .println("Main needs 2 arguments: \n"
                            + "\t 1) the directory where the crashes are saved. (String) \n"
                            + "\t 2) the similarity threshold. (double)");
        } else {
            System.out.println("Creating buckets for the reports in: "
                    + args[0] + "\nwith similarity threshold: " + args[1]);
            try {
                Set<Bucket> buckets = Bucket.createBuckets(args[0],
                        Double.parseDouble(args[1]));
                System.out.println(buckets.size()
                        + " buckets where created from: "
                        + Bucket.numReports(buckets) + " reports.");
                Bucket.printBuckets(buckets, "buckets.txt");
            } catch (NumberFormatException e) {
                System.out
                        .println("The second argument to main should be a double.");
            } catch (IOException e) {
                System.out
                        .println("Are you sure of the directory you are asking to scan?");
            }
        }
    }
}

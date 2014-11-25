package github.tdurieux.crashGraph.entities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import github.tdurieux.graph.Graph;

/* The Bucket class only keeps track of the representing graph
 * and the Id of the reports inserted in the bucket. This should
 * be all the information it needs, if unexpectedly more 
 * information is needed it can ask to load the report again.
 * This saves up on memory in the context of BIG data. 
 */
public class Bucket extends Graph<Method> {

    /**
     * Set of crash reports
     */
    Set<Integer> reportIds;

    public Bucket(Report initialReport) {
        this.graph = initialReport.getLastTrace().getEdges();
        this.reportIds = new HashSet<Integer>();
        this.reportIds.add(initialReport.getBugId());
    }

    /**
     * Create buckets
     * @param reportDir, directory which contains crash reports
     * @param similarityTreshold, similarityTreshold accepted
     * @return list of created buckets
     * @throws IOException 
     */
    public static Set<Bucket> createBuckets(String reportDir,
            double similarityTreshold) throws IOException {
        Set<Bucket> buckets = new HashSet<>();
        // for each crash report contained in directory
        for (String reportName : Report.getReportNames(reportDir)) {
            System.out.println("Scanning report: " + reportName);
            // open the report
            Report report = Report.openReport(reportName);
            boolean reportBucketed = false;
            for (Bucket bucket : buckets) {
                if (bucket.fits(report, similarityTreshold)) {
                    // similar tree
                    bucket.add(report);
                    reportBucketed = true;
                    break;
                }
            }
            if (!reportBucketed) {
                // the report is not in a bucket
                System.out.println("Creating a new Bucket.");
                // create a new bucket
                buckets.add(new Bucket(report));
            }
        }
        return buckets;
    }

    public static int numReports(Set<Bucket> buckets) {
        int numReports = 0;
        for (Bucket bucket : buckets) {
            numReports += bucket.reportIds.size();
        }
        return numReports;
    }

    public static void printBuckets(Set<Bucket> buckets, String fileName)
            throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (Bucket bucket : buckets) {
            writer.println(bucket.toString());
        }
        writer.close();
    }

    public Set<Integer> getReportIds() {
        return reportIds;
    }

    /**
     * Indicate if a crash report is to be inserted into a bucket
     * @param report The report to be analyzed
     * @param similarityTreshold similarity treshold accepted
     * @return Boolean indicating if the report should be inserted into the bucket.
     */
    public boolean fits(Report report, double similarityTreshold) {
        return similarity(report.getLastTrace()) >= similarityTreshold;
    }

    /**
     * Add a report in a bucket
     * @param report 
     */
    public void add(Report report) {
        // Add bugId is list of report Id
        reportIds.add(report.getBugId());
        // construct the new bucket graph
        this.union(report.getLastTrace());
    }

    public String toString() {
        String output = "";
        for (Integer integer : reportIds) {
            output += integer.toString() + ".json ";
        }
        return output;
    }
}

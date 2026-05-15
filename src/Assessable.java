
public interface Assessable {

    // Returns true if client is within the coverage limit
    boolean isWithinCoverageLimit();

    // Prints a summary of the client
    void printSummary();
}
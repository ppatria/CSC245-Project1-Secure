import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CSC245_Project1 {

    public static void main(String[] args) {
        // Read the filename from the command line argument
        if (args.length < 1) {
            // Handle error
            System.out.println("No file entered, please input filename");
            System.exit(0);
        }
        String filename = args[0];

        // IDS-50J SOLUTION: URL https://wiki.sei.cmu.edu/confluence/display/java/IDS50-J.+Use+conservative+file+naming+conventions
        Pattern filePattern = Pattern.compile("[^A-Za-z0-9._]");
        Matcher fileMatcher = filePattern.matcher(filename);
        if (fileMatcher.find()) {
            // File name contains bad chars; handle error
            System.out.println("Invalid filename: " + filename);
            System.exit(0);
        }

        BufferedReader inputStream = null;

        String fileLine;
        try {
            inputStream = new BufferedReader(new FileReader(filename));

            System.out.println("Email Addresses:");
            // Read one Line using BufferedReader
            while ((fileLine = inputStream.readLine()) != null) {

// VULNERABILITY IDS50-J: Does not sanitize and restrict email addresses to certain characters before printing them onto terminal
// VULNERABILITY IDS01-J: Does not blacklist tags before printing emails
// VULNERABILITY IDS51-J: No input sanitation for scripts, vulnerable to cross-site scripting attacks (XSS)
// VULNERABILITY FIO16-J: Doesn't normalize user input to prevent double dots .. that can change directory pathing



                // Normalize
                fileLine = Normalizer.normalize(fileLine, Normalizer.Form.NFKC);

                // IDS01-j SOLUTION: URL https://wiki.sei.cmu.edu/confluence/display/java/IDS01-J.+Normalize+strings+before+validating+them
                // FIO16-J SOLUTION: URL https://wiki.sei.cmu.edu/confluence/pages/viewpage.action?pageId=88487725

                // Validate
                // https://www.baeldung.com/java-email-validation-regex
               Pattern emailPattern = Pattern.compile("[^A-Za-z0-9._@]|@{2}|\\.{2}");
               Matcher emailMatcher = emailPattern.matcher(fileLine);
               if (emailMatcher.find()) {
                   // Found blacklisted tag
                   System.out.println("Email address contains unauthorized character");
                   continue;
                   // throw new IllegalStateException();
               }



                System.out.println(fileLine);
            }
        } catch (IOException io) {
            System.out.println("File IO exception" + io.getMessage());

// VULNERABILITY ERRO1-J: Will show the user the exception message, giving them unneeded backend information

        } finally {
            // Need another catch for closing
            // the streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException io) {
                System.out.println("Issue closing the Files" + io.getMessage());
                // VULNERABILITY ERRO1-J: Will show the user the exception message, giving them unneeded backend information
            }

        }
    }
}
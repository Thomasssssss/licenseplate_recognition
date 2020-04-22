import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        File inFile = null;

        if (0 < args.length) {
            inFile = new File(args[0]);
        } else {
            System.out.println("Welcome to the LicenseTemplateReader of Thomas-Andreas Pötsch (171wita22)!");
            System.out.println("Please give me as first argument the filepath which should be processed.");
            System.exit(-1);
        }

        BufferedReader br = null;
        try {

            String pattern = "[A-Z]{1,2}[-][A-Z0-9]{3,6}";
            Pattern p = Pattern.compile(pattern);

            String CurrentLine;
            ArrayList<String> licenseplates = new ArrayList();
            br = new BufferedReader(new FileReader(inFile));
            Matcher m;
            while ((CurrentLine = br.readLine()) != null) {
                for (String licenseplate: CurrentLine.split(" ")) {
                    m = p.matcher(licenseplate);
                    if(m.matches()){
                        licenseplates.add(licenseplate);
                    }
                }
            }
            Collections.sort(licenseplates);
            String coutryCode = null;
            String previous = null;
            int i = 0;
            for (String licenseplate:licenseplates) {
                if(coutryCode == null) {
                    coutryCode = licenseplate.substring(0, licenseplate.lastIndexOf("-"));
                    System.out.println(coutryCode);
                }else if(!coutryCode.equals(licenseplate.substring(0, licenseplate.lastIndexOf("-")))) {
                    coutryCode = licenseplate.substring(0, licenseplate.lastIndexOf("-"));
                    System.out.println("Number of Licenseplates: " + i +"\n");
                    System.out.println(coutryCode);
                    i = 0;
                }
                if(previous == null){
                    previous = licenseplate;
                    System.out.println("  " + licenseplate);
                    i++;
                }else if(previous.equals(licenseplate)) {
                }else {
                    System.out.println("  " + licenseplate);
                    previous = licenseplate;
                    i++;
                }
            }
            System.out.println("Number of Licenseplates: " + i);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

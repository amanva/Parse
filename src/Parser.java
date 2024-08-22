import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static void main(String[] args) {
        String flowLog = "src/file.txt";
        String tableFile = "src/table.txt";
        int untaggedCounter = 0;
        Map<String, String> lookup = getLookTable(tableFile);
        Map<String, Integer> tagCounter = new HashMap<>();
        Map<String, Integer> protocolCounter = new HashMap<>();

        try (BufferedReader lineReader = new BufferedReader(new FileReader(flowLog))) {
            String line = lineReader.readLine();
            while (line != null) {
                String[] flowLogData = line.split(" ");
                String protocol = flowLogData[7];
                String dstPort = flowLogData[5];

                // Converting the numbers in the flow log into the protocol
                if(protocol.equals("6")){
                    protocol = "tcp";
                }
                else if(protocol.equals("17")){
                    protocol = "udp";
                }
                else if(protocol.equals("1")){
                    protocol = "icmp";
                }
                //sv_p1, 1
                String comboKey = dstPort + "," + protocol;
                String tag = lookup.getOrDefault(comboKey, "Untagged");
                tagCounter.put(tag, tagCounter.getOrDefault(tag, 0) + 1);
                String portKey = dstPort + "," + protocol;
                protocolCounter.put(portKey, protocolCounter.getOrDefault(portKey, 0) + 1);
                // increment untagged
                if (tag.equals("Untagged")) {
                    untaggedCounter++;
                }
                line = lineReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        textPrinter(tagCounter,protocolCounter);
    }
    // method to print the text according to the format
    private static void textPrinter(Map<String, Integer> tagger, Map<String, Integer> proto) {
        System.out.println("Tag Counts:");
        System.out.println("Tag,Count");
        for (Map.Entry<String, Integer> entry : tagger.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println();
        System.out.println("Port/Protocol Combination Counts:");
        System.out.println("Port,Protocol,Count");
        for (Map.Entry<String, Integer> entry : proto.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }
    // Method to get the relevant data from the lookup table
    private static Map<String, String> getLookTable(String filePath) {
        Map<String, String> resultTable = new HashMap<>();
        try (BufferedReader lineReader = new BufferedReader(new FileReader(filePath))) {
            String line = lineReader.readLine();
            while (line!= null) {
                String[] lookupData = line.split(",");
                String dstPort = lookupData[0];
                String protocol = lookupData[1].toLowerCase();
                String tag = lookupData[2];
                String key = dstPort + "," + protocol;
                resultTable.put(key, tag);
                line = lineReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultTable;
    }

}
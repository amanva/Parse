import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static void main(String[] args) {
        String flowLog = "src/file.txt";
        String tableFile = "src/table.txt";

        Map<String, String> lookup = getLookTable(tableFile);
        Map<String, Integer> tagCounter = new HashMap<>();
        Map<String, Integer> protocolCounter = new HashMap<>();
        int untaggedCounter = 0;

        try (BufferedReader lineReader = new BufferedReader(new FileReader(flowLog))) {
            String line = lineReader.readLine();
            while (line != null) {
                String[] flowLogData = line.split(" ");
                String protocol = flowLogData[7];
                String dstport = flowLogData[5];

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
                String comboKey = dstport + "," + protocol;
                String tag = lookup.getOrDefault(comboKey, "Untagged");

                tagCounter.put(tag, tagCounter.getOrDefault(tag, 0) + 1);

                String portProtocolKey = dstport + "," + protocol;
                protocolCounter.put(portProtocolKey, protocolCounter.getOrDefault(portProtocolKey, 0) + 1);

                if (tag.equals("Untagged")) {
                    untaggedCounter++;
                }
                line = lineReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tag Counts:");
        System.out.println("Tag,Count");
        for (Map.Entry<String, Integer> entry : tagCounter.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println();
        System.out.println("Port/Protocol Combination Counts:");
        System.out.println("Port,Protocol,Count");
        for (Map.Entry<String, Integer> entry : protocolCounter.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    // Method to get the relevant data from the lookup table
    private static Map<String, String> getLookTable(String filePath) {
        Map<String, String> resultTable = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String dstport = fields[0];
                String protocol = fields[1].toLowerCase();
                String tag = fields[2];
                String key = dstport + "," + protocol;
                resultTable.put(key, tag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultTable;
    }

}
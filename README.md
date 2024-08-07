# LoveBite is a DDoS Attack Simulation Tool 🚀

This Java-based DDoS (Distributed Denial of Service) attack simulation tool is designed for educational purposes. It allows users to simulate DDoS attacks by sending multiple HTTP requests to a target `URL`. The tool includes features such as proxy support, firewall bypass methods, and logging capabilities.

## Features ✨

- **HTTP and HTTPS attack methods**
- **Proxy support**
- **Firewall bypass techniques**
- **Multi-threaded attack using ExecutorService**
- **Logging of attack details**
- **Color-coded console output for status**

## Installation 🛠️

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Internet connection

### Steps

1. **Clone the repository:**

    ```sh
    git clone https://github.com/md-rs01/LoveBite.git
    ```

2. **Navigate to the project directory:**

    ```sh
    cd LoveBite
    ```

3. **Compile the Java code:**

    ```sh
    javac Dos.java
    ```

4. **Run the program:**

    ```sh
    java Dos
    ```

   **NB:-**
   ```sh
   shudo ulimit -n 999999
   shudo ulimit -u 999999
   ```

   
## Usage 🧑‍💻

When you run the program, you will be prompted to enter:

1. **Target URL**: The URL of the website to attack.
2. **Number of Threads**: Concurrent threads for the attack.
3. **Attack Method**: `GET` or `POST`.

### Example

  ```sh
  Enter Web URL: http://example.com
  Thread => 100
  Method: GET
  ```
## Proxy Support 🌐
  ### To use proxies:-
  create a file with each proxy in the format `proxy_host:proxy_port` , one per line. 
  
For Instance:-  **proxies.txt**
- 192.168.1.1:8080
- 203.0.113.5:3128
- 198.51.100.12:8080

The tool will utilize these proxies during the attack. Provide the proxy file path when prompted by the program.

### Logging 📝
The tool logs attack details to *attack_log.txt*  with the following format:-

``` Timestamp: 2024-08-02 12:34:56
    URL: http://example.com
    Total Requests: 100
    Successful Requests: 90
    Failed Requests: 10
    =========================================
```
### Firewall Bypass Methods 🔓

The tool includes basic firewall bypass techniques:- 

**Randomizing User-Agents:**
To avoid detection by varying `user-agent` strings.Varying Request Methods: Switching between `GET` and `POST` requests.Using Proxies: To mask the origin of requests.

### Code Structure 🏗️

- Dos.java: 
  - Main class for DDoS attack
- simulation.main: 
  - Handles input, 
  - initializes attack parameters, 
  - starts threads.
- checkConnection & sslCheckConnection: 
  - Check connection to target URL.
- postAttack & getAttack: 
  - Perform HTTP POST and GET attacks.
- sslPostAttack & sslGetAttack: 
  - Perform HTTPS POST and GET attacks.
- firewallBypass: 
  - Applies firewall bypass techniques.
- loadProxies & saveGoodProxies: 
  - Manage proxy lists.

### Contact 📬
**Author:-** BoyfromBd

**Telegram:-** [HeartCrafter](https://t.me/heartcrafter)

**Proton-Main:- ** [BoyFromBD](mailto:HeartCrafter@proton.com)

### Disclaimer ⚠️
This tool is intended for educational purposes only. Use it responsibly and only on websites where you have permission. The author is not responsible for any misuse of this tool.

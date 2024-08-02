import requests
import argparse
from concurrent.futures import ThreadPoolExecutor, as_completed
import signal
import sys

# Read proxies from the input file
def load_proxies(filename):
    with open(filename, 'r') as f:
        proxies = [line.strip() for line in f]
    return proxies

# Check if a proxy is good
def check_proxy(proxy):
    proxies = {
        "http": f"http://{proxy}",
        "https": f"http://{proxy}",
    }
    try:
        response = requests.get("http://www.google.com", proxies=proxies, timeout=10)
        if response.status_code == 200:
            print(f"Good proxy: {proxy}")
            append_good_proxy(proxy, 'good_proxy.txt')
            return proxy
    except requests.RequestException:
        pass
    print(f"Bad proxy: {proxy}")
    return None

# Append a good proxy to the output file
def append_good_proxy(proxy, filename):
    with open(filename, 'a') as f:
        f.write(f"{proxy}\n")

# Save good proxies to the output file
def save_good_proxies(good_proxies, filename):
    with open(filename, 'w') as f:
        for proxy in good_proxies:
            f.write(f"{proxy}\n")

# Signal handler for graceful shutdown on Ctrl+C
def signal_handler(sig, frame):
    print('Process interrupted by user. Exiting...')
    sys.exit(0)

def main():
    parser = argparse.ArgumentParser(description='Check proxies and save good ones to a file.')
    parser.add_argument('-p', '--proxy', type=str, required=True, help='Path to the proxy file')
    parser.add_argument('-t', '--threads', type=int, default=50, help='Number of threads for concurrent checking')
    args = parser.parse_args()

    signal.signal(signal.SIGINT, signal_handler)

    proxies = load_proxies(args.proxy)
    
    # Use ThreadPoolExecutor to check proxies concurrently
    with ThreadPoolExecutor(max_workers=args.threads) as executor:
        future_to_proxy = {executor.submit(check_proxy, proxy): proxy for proxy in proxies}
        
        for future in as_completed(future_to_proxy):
            try:
                future.result()
            except Exception as exc:
                print(f'Generated an exception: {exc}')

    print(f"Good proxies saved to good_proxy.txt")

if __name__ == "__main__":
    main()

import requests
import argparse
from concurrent.futures import ThreadPoolExecutor

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
        response = requests.get("http://www.google.com", proxies=proxies, timeout=5)
        if response.status_code == 200:
            print(f"Good proxy: {proxy}")
            return proxy
    except requests.RequestException:
        pass
    print(f"Bad proxy: {proxy}")
    return None

# Save good proxies to the output file
def save_good_proxies(good_proxies, filename):
    with open(filename, 'w') as f:
        for proxy in good_proxies:
            f.write(f"{proxy}\n")

def main():
    parser = argparse.ArgumentParser(description='Check proxies and save good ones to a file.')
    parser.add_argument('-p', '--proxy', type=str, help='Path to the proxy file')
    args = parser.parse_args()

    if not args.proxy:
        parser.print_usage()
        return

    proxies = load_proxies(args.proxy)
    
    # Use ThreadPoolExecutor to check proxies concurrently
    with ThreadPoolExecutor(max_workers=20) as executor:
        results = list(executor.map(check_proxy, proxies))

    good_proxies = [proxy for proxy in results if proxy]
    save_good_proxies(good_proxies, 'good_proxy.txt')

    print(f"Good proxies saved to good_proxy.txt")

if __name__ == "__main__":
    main()

#!/bin/bash

URL="https://polysnap-399912.oa.r.appspot.com/api/message-published/all-users"
TOTAL_REQUESTS=15
BATCH_SIZE=15

# Function to send requests in a batch
send_requests() {
    start=$1
    end=$2
    for ((i=start; i<=end; i++)); do
        echo "Sending GET request $i"
        curl -X GET "$URL"
    done
}

# Loop to send requests in batches of 100 concurrently
for ((i=0; i<TOTAL_REQUESTS; i+=BATCH_SIZE)); do
    end=$((i + BATCH_SIZE - 1))
    send_requests $i $end & # Send requests in the background
    echo "=========================== $end requests ==========================="
done

echo "All requests sent successfully."
$uri = "http://localhost:8080/api/alerts"

$alert = @{
    id = "prom-flow-001"
    source = "prometheus"
    alertName = "PaymentServiceCPUHigh"
    service = "payment-service"
    severity = "CRITICAL"
    priority = "CRITICAL"
    status = "OPEN"
    message = "CPU usage exceeded 95% for 5 minutes"
    occurrenceCount = 1
    flapping = $false
    groupId = "payment-service"
    firstSeen = "2026-09-05T10:00:00"
    lastSeen = "2026-09-05T10:00:00"
}

$json = $alert | ConvertTo-Json

$response = Invoke-RestMethod `
    -Method Post `
    -Uri $uri `
    -ContentType "application/json" `
    -Body $json

$response | ConvertTo-Json -Depth 10
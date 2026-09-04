PERSON 2 - NOTIFICATION & SMART ROUTING

Input:
Processed alert from Person 1's common Alert JSON.

Responsibilities:
1. Respect Person 1's notification/cooldown decision.
2. Route based on severity.
3. Format notifications.
4. Format digest/batched notifications.
5. Deliver notifications.
6. Record delivery result.

Routing:
CRITICAL -> PagerDuty
HIGH -> Slack
MEDIUM -> Slack
LOW -> Email/Discord

Delivery statuses:
SENT
FAILED
SKIPPED

Person 2 does NOT own:
- Deduplication
- Cooldown calculation
- Severity calculation
- Correlation
- Root-cause detection
- Alert model/API definition
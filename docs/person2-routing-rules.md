# Person 2 — Smart Routing Rules

## Routing Priority

1. Check Person 1's notification decision first.
2. If should_notify is false, skip delivery.
3. If should_notify is true, route based on severity.

## Severity Routing

CRITICAL → PagerDuty
HIGH → Slack
MEDIUM → Slack
LOW → Email/Discord

## Delivery Status

SENT
FAILED
SKIPPED

## Ownership

Person 1 owns:
- Severity calculation
- Deduplication
- Grouping
- Cooldown
- Notification decision

Person 2 owns:
- Routing
- Notification formatting
- Notification delivery
- Delivery result

Person 3 owns:
- Logging
- Monitoring
- Dashboard
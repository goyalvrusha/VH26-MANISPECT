# Notification Routing Rules

## Step 1 — Check Notification Decision

Always check:

notification.shouldNotify

- true → continue to routing
- false → do not send notification

Person 2 must not recreate deduplication, cooldown,
grouping, or flapping logic.

## Step 2 — Route Based on Priority

| Priority | Notification Channel |
|----------|----------------------|
| CRITICAL | PagerDuty |
| HIGH | Slack |
| MEDIUM | Slack |
| LOW | Email |

## Step 3 — Delivery Result

Every notification attempt should eventually produce:

- SENT
- FAILED
- SKIPPED

## Ownership

Person 1:
- Alert intelligence
- Severity/priority calculation
- Deduplication
- Grouping
- Cooldown
- Flapping detection
- shouldNotify decision

Person 2:
- Routing
- Notification formatting
- Notification delivery
- Delivery result

Person 3:
- Logging
- Monitoring
- Dashboard
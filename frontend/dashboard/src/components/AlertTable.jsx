import React, { useState } from "react";
import "./AlertTable.css";

function AlertTable({ alerts = [] }) {
  const [searchTerm, setSearchTerm] = useState("");
  const [severityFilter, setSeverityFilter] = useState("ALL");

  const filteredAlerts = alerts.filter((alert) => {
    const matchesSearch =
      alert.alertName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      alert.service.toLowerCase().includes(searchTerm.toLowerCase()) ||
      alert.message.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesSeverity =
      severityFilter === "ALL" || alert.severity === severityFilter;

    return matchesSearch && matchesSeverity;
  });

  const getSeverityBadgeClass = (severity) => {
    switch (severity) {
      case "CRITICAL":
        return "badge critical";
      case "WARNING":
        return "badge warning";
      default:
        return "badge info";
    }
  };

  return (
    <div className="alert-table-container">
      <div className="alert-table-header">
        <h2 className="alert-table-title">Live Alert Feed ({filteredAlerts.length})</h2>
        
        <div className="alert-table-filters">
          <input
            type="text"
            className="search-input"
            placeholder="Search by name, service, message..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <select
            className="filter-select"
            value={severityFilter}
            onChange={(e) => setSeverityFilter(e.target.value)}
          >
            <option value="ALL">All Severities</option>
            <option value="CRITICAL">Critical</option>
            <option value="WARNING">Warning</option>
            <option value="INFO">Info</option>
          </select>
        </div>
      </div>

      <table className="alert-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Alert Name</th>
            <th>Service</th>
            <th>Severity</th>
            <th>Status</th>
            <th>Occurrences</th>
            <th>Flapping</th>
            <th>Group ID</th>
            <th>Notification Decision</th>
          </tr>
        </thead>
        <tbody>
          {filteredAlerts.length === 0 ? (
            <tr>
              <td colSpan="9" style={{ textAlign: "center", color: "#8a8aa8", padding: "20px" }}>
                No alerts found matching current filters.
              </td>
            </tr>
          ) : (
            filteredAlerts.map((alert) => (
              <tr key={alert.id}>
                <td><span className="code-pill">{alert.id}</span></td>
                <td>
                  <strong>{alert.alertName}</strong>
                  <div style={{ fontSize: "11px", color: "#8a8aa8" }}>{alert.message}</div>
                </td>
                <td>{alert.service}</td>
                <td>
                  <span className={getSeverityBadgeClass(alert.severity)}>
                    {alert.severity}
                  </span>
                </td>
                <td>
                  <span style={{ color: alert.status === "FIRING" ? "#ef4444" : "#10b981", fontWeight: 600 }}>
                    {alert.status}
                  </span>
                </td>
                <td>{alert.occurrenceCount}x</td>
                <td>
                  {alert.flapping ? (
                    <span className="badge flapping">FLAPPING</span>
                  ) : (
                    <span style={{ color: "#6b7280" }}>No</span>
                  )}
                </td>
                <td>
                  {alert.groupId ? (
                    <span className="code-pill">{alert.groupId}</span>
                  ) : (
                    <span style={{ color: "#6b7280" }}>-</span>
                  )}
                </td>
                <td>
                  {alert.notification?.shouldNotify ? (
                    <span className="badge notify">SENT ({alert.notification.reason})</span>
                  ) : (
                    <span className="badge suppress">SUPPRESSED ({alert.notification?.reason || "NO_ACTION"})</span>
                  )}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default AlertTable;

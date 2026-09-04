import React from "react";
import "./StatsOverview.css";

function StatsOverview({ alerts = [] }) {
  const totalAlerts = alerts.length;
  const activeAlerts = alerts.filter((a) => a.status === "FIRING").length;
  const criticalAlerts = alerts.filter((a) => a.severity === "CRITICAL").length;
  const suppressedAlerts = alerts.filter((a) => !a.notification?.shouldNotify).length;
  const flappingAlerts = alerts.filter((a) => a.flapping).length;
  const groupedAlerts = alerts.filter((a) => a.groupId).length;

  const reductionPercentage = totalAlerts > 0 
    ? ((suppressedAlerts / totalAlerts) * 100).toFixed(1) 
    : "0.0";

  return (
    <div className="stats-container">
      <div className="stat-card">
        <div className="stat-title">Total Alerts</div>
        <div className="stat-value">{totalAlerts}</div>
        <div className="stat-subtext">Received in stream</div>
      </div>

      <div className="stat-card active">
        <div className="stat-title">Active Alerts</div>
        <div className="stat-value">{activeAlerts}</div>
        <div className="stat-subtext">Currently firing</div>
      </div>

      <div className="stat-card critical">
        <div className="stat-title">Critical Alerts</div>
        <div className="stat-value">{criticalAlerts}</div>
        <div className="stat-subtext">Urgent action needed</div>
      </div>

      <div className="stat-card suppressed">
        <div className="stat-title">Suppressed Alerts</div>
        <div className="stat-value">{suppressedAlerts}</div>
        <div className="stat-subtext">Cooldown / Correlated</div>
      </div>

      <div className="stat-card">
        <div className="stat-title">Flapping Alerts</div>
        <div className="stat-value">{flappingAlerts}</div>
        <div className="stat-subtext">High frequency toggles</div>
      </div>

      <div className="stat-card">
        <div className="stat-title">Grouped Alerts</div>
        <div className="stat-value">{groupedAlerts}</div>
        <div className="stat-subtext">Cluster grouped</div>
      </div>

      <div className="stat-card reduction">
        <div className="stat-title">Alert Reduction</div>
        <div className="stat-value">{reductionPercentage}%</div>
        <div className="stat-subtext">Noise eliminated</div>
      </div>
    </div>
  );
}

export default StatsOverview;

import mockAlerts from "../data/mockAlerts.json";
import StatsOverview from "../components/StatsOverview";
import AlertTable from "../components/AlertTable";

function Dashboard() {
  return (
    <div style={{ padding: "20px", textAlign: "left" }}>
      <header style={{ marginBottom: "20px" }}>
        <h1 style={{ margin: 0, fontSize: "28px", color: "#ffffff" }}>
          Alert Fatigue Buster
        </h1>
        <p style={{ margin: "5px 0 0 0", color: "#8a8aa8" }}>
          Real-time alert intelligence & monitoring dashboard
        </p>
      </header>

      <main>
        <StatsOverview alerts={mockAlerts} />
        <AlertTable alerts={mockAlerts} />
      </main>
    </div>
  );
}

export default Dashboard;


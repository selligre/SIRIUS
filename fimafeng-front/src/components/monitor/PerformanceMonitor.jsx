import React, { useEffect, useState } from 'react';
        import si from 'systeminformation';

        const PerformanceMonitor = () => {
            const [cpuUsage, setCpuUsage] = useState([]);

            useEffect(() => {
                const interval = setInterval(async () => {
                    const load = await si.currentLoad();
                    setCpuUsage(prevUsage => [...prevUsage, load.currentLoad]);
                }, 1000);

                return () => clearInterval(interval);
            }, []);

            return (
                <div style={{ position: 'absolute', top: 0, right: 0, backgroundColor: 'white', zIndex: 1000 }}>
                    <h2>Performance CPU</h2>
                    <ul>
                        {cpuUsage.map((usage, index) => (
                            <li key={index}>Temps: {index + 1}s - Utilisation CPU: {usage.toFixed(2)}%</li>
                        ))}
                    </ul>
                </div>
            );
        };

        export default PerformanceMonitor;
import React from 'react';
import {Bar} from 'react-chartjs-2';
import {Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend} from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const TagBarChart = ({tagsCount = []}) => {
    const data = {
        labels: tagsCount.map(tag => tag.name),
        datasets: [
            {
                label: "Nombre d'annonces par tag",
                data: tagsCount.map(tag => tag.count),
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
            },
        ],
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Nombre de tags',
            },
        },
    };

    return <Bar data={data} options={options}/>;
};

export default TagBarChart;
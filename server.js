// server.js
const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const port = 3000;

app.use(bodyParser.json());
app.use(express.static('public')); // Serve static files from the "public" directory

app.post('/query', (req, res) => {
    const columnCount = req.body.columnCount;
    const data = generateMockData(columnCount, 20);
    res.json({ data: data, rowCount: data.length });
});

function generateMockData(columns, rows) {
    const data = [];
    for (let i = 0; i < rows; i++) {
        const row = {};
        for (let j = 1; j <= columns; j++) {
            row[`Column${j}`] = generateRandomData(j);
        }
        data.push(row);
    }
    return data;
}

function generateRandomData(columnIndex) {
    switch (columnIndex) {
        case 1:
            return Math.floor(Math.random() * 1000); // Random integer
        case 2:
            return (Math.random() * 1000).toFixed(2); // Random float
        case 4:
            return Math.random() > 0.5 ? 'Yes' : 'No'; // Random boolean
        default:
            return `Data${columnIndex}-${Math.floor(Math.random() * 100)}`; // Default random string
    }
}

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
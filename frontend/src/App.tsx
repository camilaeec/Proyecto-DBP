import { BrowserRouter } from 'react-router-dom';
import AppRouter from './Router'; // Archivo con las rutas
import React from 'react';

function App() {
    return (
        <BrowserRouter>
            <AppRouter />
        </BrowserRouter>
    );
}

export default App;

import './App.css'
import { BrowserRouter } from 'react-router-dom';
import AppRouter from './Router';
import React from 'react'

function App() {
    return (
        <BrowserRouter>
            <AppRouter />
        </BrowserRouter>
    );
}

export default App;

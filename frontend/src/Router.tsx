import { Route, Routes } from 'react-router-dom';
import Component from './pages/Inicio';
import LoginPage from './pages/Login';
import CalendarPage from './pages/Calendario';
import GradesPage from './pages/Calificaciones';
import CoursesPage from './pages/Cursos';
import React from 'react';

export default function AppRouter() {
    return (
        <Routes>
            {/* Rutas de acceso público */}
            <Route path="/" element={<Component />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/calendar" element={<CalendarPage />} />
            <Route path="/grades" element={<GradesPage />} />
            <Route path="/courses" element={<CoursesPage />} />

            {/* Ruta de error 404 */}
            <Route path="*" element={<h1>404 - Página no encontrada</h1>} />
        </Routes>
    );
}

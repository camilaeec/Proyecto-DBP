"use client";

import * as React from "react";
import { Link } from "react-router-dom";
import { Bell, Calendar, Home, BookOpen, Users2, Inbox, LogOut, Menu } from "lucide-react";

import { Button } from "../components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "../components/ui/card";
import { Sheet, SheetContent, SheetTrigger } from "../components/ui/sheet";
import { Avatar, AvatarFallback, AvatarImage } from "../components/ui/avatar";
import { ScrollArea } from "../components/ui/scroll-area";

export default function GradesPage() {
    const [open, setOpen] = React.useState(false);

    const grades = [
        { id: 1, name: "EC1", description: "Evaluación continua", score: "20 / 20" },
        { id: 2, name: "EC2", description: "Evaluación continua", score: "18 / 20" },
        { id: 3, name: "Práctica 1", description: "Práctica de laboratorio", score: "15 / 20" },
    ];

    return (
        <div className="min-h-screen bg-white">
            <header className="sticky top-0 z-50 w-full border-b bg-[#323232]">
                <div className="flex h-16 items-center gap-4 px-4">
                    <Sheet open={open} onOpenChange={setOpen}>
                        <SheetTrigger asChild>
                            <Button variant="ghost" size="icon" className="shrink-0 text-white hover:bg-[#424242]">
                                <Menu className="h-5 w-5" />
                                <span className="sr-only">Abrir menú</span>
                            </Button>
                        </SheetTrigger>
                        <SheetContent side="left" className="w-[300px] p-0 bg-[#323232] text-white border-r-0">
                            <div className="border-b border-[#424242]">
                                <div className="flex h-16 items-center px-6">
                                    <img
                                        src="/placeholder.svg"
                                        alt="UTEC Logo"
                                        className="h-8 w-8"
                                        width={32}
                                        height={32}
                                    />
                                    <span className="ml-2 text-lg font-bold">Sistema Académico</span>
                                </div>
                            </div>
                            <ScrollArea className="h-[calc(100vh-4rem)]">
                                <div className="flex flex-col gap-4 p-4">
                                    <div className="flex items-center gap-4">
                                        <Avatar className="h-10 w-10">
                                            <AvatarImage src="/placeholder.svg" alt="Usuario" />
                                            <AvatarFallback>U</AvatarFallback>
                                        </Avatar>
                                        <div className="flex flex-col">
                                            <span className="text-sm font-medium">Usuario UTEC</span>
                                            <span className="text-xs text-gray-400">Alumno</span>
                                        </div>
                                    </div>
                                    <nav className="flex flex-col gap-2">
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-white hover:bg-[#424242]"
                                            to="/"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Home className="h-5 w-5" />
                                            Inicio
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-white hover:bg-[#424242]"
                                            to="/courses"
                                            onClick={() => setOpen(false)}
                                        >
                                            <BookOpen className="h-5 w-5" />
                                            Cursos
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-white hover:bg-[#424242]"
                                            to="/calendar"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Calendar className="h-5 w-5" />
                                            Calendario
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-white hover:bg-[#424242]"
                                            to="/groups"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Users2 className="h-5 w-5" />
                                            Grupos
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-white hover:bg-[#424242]"
                                            to="/inbox"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Inbox className="h-5 w-5" />
                                            Bandeja de entrada
                                        </Link>
                                        <Link
                                            className="mt-auto flex items-center gap-2 rounded-lg px-4 py-2 text-white hover:bg-[#424242]"
                                            to="/logout"
                                            onClick={() => setOpen(false)}
                                        >
                                            <LogOut className="h-5 w-5" />
                                            Cerrar Sesión
                                        </Link>
                                    </nav>
                                </div>
                            </ScrollArea>
                        </SheetContent>
                    </Sheet>
                    <div className="flex-1">
                        <h1 className="text-lg font-semibold text-white">Calificaciones</h1>
                    </div>
                    <Button variant="ghost" size="icon" className="shrink-0 text-white hover:bg-[#424242]">
                        <Bell className="h-5 w-5" />
                        <span className="sr-only">Notificaciones</span>
                    </Button>
                </div>
            </header>
            <main className="container mx-auto p-4">
                <Card className="shadow-lg">
                    <CardHeader className="bg-[#00AED8] text-white">
                        <CardTitle className="text-xl font-bold">Calificaciones para Usuario UTEC</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <table className="min-w-full divide-y divide-gray-200">
                            <thead className="bg-gray-50">
                            <tr>
                                <th className="px-6 py-3 text-left text-xs font-medium text-[#323232] uppercase tracking-wider">Nombre</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-[#323232] uppercase tracking-wider">Descripción</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-[#323232] uppercase tracking-wider">Puntaje</th>
                            </tr>
                            </thead>
                            <tbody className="bg-white divide-y divide-gray-200">
                            {grades.map((grade) => (
                                <tr key={grade.id}>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-[#323232]">{grade.name}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{grade.description}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-[#00AED8] font-semibold">{grade.score}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </CardContent>
                </Card>
            </main>
        </div>
    );
}

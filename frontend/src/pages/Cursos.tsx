"use client";

import * as React from "react";
import { Link } from "react-router-dom";
import { Bell, Calendar, Home, BookOpen, Users2, Inbox, LogOut, Menu, MoreVertical } from "lucide-react";

import { Button } from "../components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from "../components/ui/card";
import { Sheet, SheetContent, SheetTrigger } from "../components/ui/sheet";
import { Avatar, AvatarFallback, AvatarImage } from "../components/ui/avatar";
import { ScrollArea } from "../components/ui/scroll-area";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger,
} from "../components/ui/dropdown-menu";

export default function CoursesPage() {
    const [open, setOpen] = React.useState(false);

    // Ejemplo de cursos (en una aplicación real, estos vendrían de una API o base de datos)
    const courses = [
        { id: 1, name: "Algoritmos y Estructuras de Datos", code: "CS2023", profesor: "Dr. Ana García", averageGrade: 15.5 },
        { id: 2, name: "Desarrollo Basado en Plataformas", code: "CS2031", profesor: "Ing. Carlos Rodríguez", averageGrade: 14.2 },
        { id: 3, name: "Empresa y Consumidor", code: "GI2101", profesor: "Lic. María López", averageGrade: 16.8 },
        { id: 4, name: "Arte y Tecnología", code: "HH3101", profesor: "Mg. Pedro Sánchez", averageGrade: 17.5 },
        { id: 5, name: "Competencias Digitales", code: "CD2024", profesor: "Ing. Laura Martínez", averageGrade: 15.7 },
        { id: 6, name: "Comunicación Pre UTEC", code: "CP2221", profesor: "Lic. Jorge Ramírez", averageGrade: 16.3 },
    ];

    return (
        <div className="min-h-screen bg-gray-100">
            <header className="sticky top-0 z-50 w-full border-b bg-white">
                <div className="flex h-16 items-center gap-4 px-4">
                    <Sheet open={open} onOpenChange={setOpen}>
                        <SheetTrigger asChild>
                            <Button variant="ghost" size="icon" className="shrink-0">
                                <Menu className="h-5 w-5" />
                                <span className="sr-only">Abrir menú</span>
                            </Button>
                        </SheetTrigger>
                        <SheetContent side="left" className="w-[300px] p-0">
                            <div className="border-b">
                                <div className="flex h-16 items-center px-6">
                                    <img
                                        src="../assets/utec-logo.png"
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
                                            <span className="text-xs text-gray-500">Alumno</span>
                                        </div>
                                    </div>
                                    <nav className="flex flex-col gap-2">
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            to="/"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Home className="h-5 w-5" />
                                            Inicio
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg bg-gray-100 px-4 py-2 text-gray-900"
                                            to="/courses"
                                            onClick={() => setOpen(false)}
                                        >
                                            <BookOpen className="h-5 w-5" />
                                            Cursos
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            to="/calendar"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Calendar className="h-5 w-5" />
                                            Calendario
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            to="/groups"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Users2 className="h-5 w-5" />
                                            Grupos
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            to="/inbox"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Inbox className="h-5 w-5" />
                                            Bandeja de entrada
                                        </Link>
                                        <Link
                                            className="mt-auto flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
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
                        <h1 className="text-lg font-semibold">Cursos</h1>
                    </div>
                    <Button variant="ghost" size="icon" className="shrink-0">
                        <Bell className="h-5 w-5" />
                        <span className="sr-only">Notificaciones</span>
                    </Button>
                </div>
            </header>
            <main className="container mx-auto p-4">
                <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                    {courses.map((course) => (
                        <Card key={course.id} className="flex flex-col justify-between">
                            <CardHeader>
                                <CardTitle>{course.name}</CardTitle>
                                <CardDescription>{course.code}</CardDescription>
                            </CardHeader>
                            <CardContent>
                                <p className="text-sm text-gray-500">Profesor: {course.profesor}</p>
                                <p className="mt-2 text-lg font-semibold">Promedio: {course.averageGrade.toFixed(1)}</p>
                            </CardContent>
                            <CardFooter className="flex justify-between">
                                <Button variant="outline">Ver curso</Button>
                                <DropdownMenu>
                                    <DropdownMenuTrigger asChild>
                                        <Button variant="ghost" size="icon">
                                            <MoreVertical className="h-4 w-4" />
                                        </Button>
                                    </DropdownMenuTrigger>
                                    <DropdownMenuContent align="end">
                                        <DropdownMenuItem>Ver calificaciones</DropdownMenuItem>
                                        <DropdownMenuItem>Ver tareas pendientes</DropdownMenuItem>
                                        <DropdownMenuItem>Contactar profesor</DropdownMenuItem>
                                    </DropdownMenuContent>
                                </DropdownMenu>
                            </CardFooter>
                        </Card>
                    ))}
                </div>
            </main>
        </div>
    );
}

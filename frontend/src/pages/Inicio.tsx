"use client"

import { useState } from 'react'
import { Link } from 'react-router-dom'
import { Bell, Calendar, Home, BookOpen, Users2, LogOut, Menu, X, Inbox } from 'lucide-react'

import { Button } from '../components/ui/button'
import { Card } from '../components/ui/card'
import { Sheet, SheetContent, SheetTrigger } from '../components/ui/sheet'
import { Avatar, AvatarFallback, AvatarImage } from '../components/ui/avatar'
import React from 'react'

export default function Dashboard() {
    const [open, setOpen] = useState(false)

    return (
        <div className="min-h-screen bg-white">
            {/* Header */}
            <header className="sticky top-0 z-50 w-full border-b bg-white">
                <div className="flex h-16 items-center px-4">
                    <Sheet open={open} onOpenChange={setOpen}>
                        <SheetTrigger asChild>
                            <Button variant="ghost" size="icon" className="lg:hidden">
                                <Menu className="h-5 w-5" />
                                <span className="sr-only">Toggle menu</span>
                            </Button>
                        </SheetTrigger>
                        <SheetContent
                            className="w-[300px] p-0 fixed inset-y-0 left-0"
                        >
                            <div className="flex items-center justify-between border-b p-4">
                                <span className="text-lg font-semibold">UTEC++</span>
                                <Button variant="ghost" size="icon" onClick={() => setOpen(false)}>
                                    <X className="h-4 w-4" />
                                    <span className="sr-only">Close</span>
                                </Button>
                            </div>
                            <div className="border-b px-4 py-3">
                                <div className="flex items-center gap-2">
                                    <Avatar>
                                        <AvatarImage src="/placeholder-user.jpg" alt="Usuario UTEC" />
                                        <AvatarFallback>U</AvatarFallback>
                                    </Avatar>
                                    <div>
                                        <p className="text-sm font-medium">Usuario UTEC</p>
                                        <p className="text-sm text-gray-500">Alumno</p>
                                    </div>
                                </div>
                            </div>
                            <nav className="flex flex-col">
                                <Link
                                    to="/"
                                    className="flex items-center gap-3 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                >
                                    <Home className="h-4 w-4" />
                                    Inicio
                                </Link>
                                <Link
                                    to="/courses"
                                    className="flex items-center gap-3 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                >
                                    <BookOpen className="h-4 w-4" />
                                    Cursos
                                </Link>
                                <Link
                                    to="/calendar"
                                    className="flex items-center gap-3 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                >
                                    <Calendar className="h-4 w-4" />
                                    Calendario
                                </Link>
                                <Link
                                    to="/groups"
                                    className="flex items-center gap-3 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                >
                                    <Users2 className="h-4 w-4" />
                                    Grupos
                                </Link>
                                <Link
                                    to="/inbox"
                                    className="flex items-center gap-3 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                >
                                    <Inbox className="h-4 w-4" />
                                    Bandeja de entrada
                                </Link>
                                <Link
                                    to="/logout"
                                    className="flex items-center gap-3 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                >
                                    <LogOut className="h-4 w-4" />
                                    Cerrar Sesión
                                </Link>
                            </nav>
                        </SheetContent>
                    </Sheet>
                    <span className="text-xl font-semibold ml-4">Tablero</span>
                    <div className="ml-auto flex items-center gap-4">
                        <Button variant="ghost" size="icon">
                            <Bell className="h-5 w-5" />
                            <span className="sr-only">Notificaciones</span>
                        </Button>
                        <Avatar>
                            <AvatarImage src="/placeholder-user.jpg" alt="Usuario" />
                            <AvatarFallback>U</AvatarFallback>
                        </Avatar>
                    </div>
                </div>
            </header>

            {/* Main Content */}
            <main className="container mx-auto p-6">
                <div className="space-y-8">
                    {/* Today's Section */}
                    <section>
                        <h2 className="text-lg font-semibold mb-1">Hoy</h2>
                        <p className="text-sm text-gray-500 mb-4">20 de noviembre</p>
                        <div className="space-y-4">
                            <Card className="p-4">
                                <div className="flex items-center justify-between">
                                    <div>
                                        <h3 className="font-medium">ALGORITMOS Y ESTRUCTURAS DE DATOS</h3>
                                        <p className="text-sm text-gray-500">Laboratorio 2.01 - 2024-2</p>
                                    </div>
                                    <Button variant="secondary" size="sm" className="bg-black text-white hover:bg-gray-800">
                                        Ver detalles
                                    </Button>
                                </div>
                            </Card>
                            <Card className="p-4">
                                <div className="flex items-center justify-between">
                                    <div>
                                        <h3 className="font-medium">DESARROLLO BASADO EN PLATAFORMAS</h3>
                                        <p className="text-sm text-gray-500">Actividad en clase S14B</p>
                                    </div>
                                    <Button variant="secondary" size="sm" className="bg-black text-white hover:bg-gray-800">
                                        Ver detalles
                                    </Button>
                                </div>
                            </Card>
                        </div>
                    </section>

                    {/* Upcoming Activities Section */}
                    <section>
                        <h2 className="text-lg font-semibold mb-4">Próximas actividades</h2>
                        <Card className="p-4">
                            <div className="flex items-center justify-between">
                                <div>
                                    <h3 className="font-medium">EMPRESA Y CONSUMIDOR</h3>
                                    <p className="text-sm text-gray-500">Dinámica - GRUPAL N°9</p>
                                </div>
                                <Button variant="secondary" size="sm" className="bg-black text-white hover:bg-gray-800">
                                    Ver detalles
                                </Button>
                            </div>
                        </Card>
                    </section>
                </div>
            </main>
        </div>
    )
}


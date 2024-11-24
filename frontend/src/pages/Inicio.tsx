import Link from "next/link"
import Image from "next/image"
import { Bell, Calendar, FileText, Home, LogOut, Menu, Users2, BookOpen, Clock, Plus } from 'lucide-react'

import { Button } from "../components/ui/button"
import { Card, CardContent } from "../components/ui/card"
import { Avatar, AvatarFallback, AvatarImage } from "../components/ui/avatar"
import React from "react"

export default function Component() {
    return (
        <div className="min-h-screen bg-white">
            {/* Top Navigation */}
            <header className="sticky top-0 z-50 w-full border-b bg-white">
                <div className="flex h-16 items-center px-4 md:px-6">
                    <Button variant="ghost" size="icon" className="mr-4 md:hidden">
                        <Menu className="h-5 w-5" />
                    </Button>
                    <div className="flex items-center gap-2">
                        <Image
                            src="/placeholder.svg"
                            alt="UTEC Logo"
                            className="h-8 w-8"
                            width={32}
                            height={32}
                        />
                        <span className="text-lg font-bold">UTEC</span>
                    </div>
                    <div className="ml-auto flex items-center gap-4">
                        <Button variant="ghost" size="icon">
                            <Bell className="h-5 w-5" />
                        </Button>
                        <Avatar>
                            <AvatarImage src="/placeholder.svg" alt="Usuario" />
                            <AvatarFallback>U</AvatarFallback>
                        </Avatar>
                    </div>
                </div>
            </header>
            <div className="grid lg:grid-cols-[240px_1fr]">
                {/* Sidebar */}
                <aside className="hidden lg:block border-r bg-gray-100/40 lg:col-span-1">
                    <nav className="flex flex-col gap-2 p-4">
                        <Link
                            className="flex items-center gap-2 rounded-lg bg-gray-100 px-4 py-2 text-gray-900"
                            href="#"
                        >
                            <Home className="h-5 w-5" />
                            Inicio
                        </Link>
                        <Link
                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                            href="#"
                        >
                            <BookOpen className="h-5 w-5" />
                            Cursos
                        </Link>
                        <Link
                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                            href="#"
                        >
                            <Calendar className="h-5 w-5" />
                            Calendario
                        </Link>
                        <Link
                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                            href="#"
                        >
                            <Users2 className="h-5 w-5" />
                            Grupos
                        </Link>
                        <Link
                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                            href="#"
                        >
                            <FileText className="h-5 w-5" />
                            Trámites
                        </Link>
                        <Link
                            className="mt-auto flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                            href="#"
                        >
                            <LogOut className="h-5 w-5" />
                            Cerrar Sesión
                        </Link>
                    </nav>
                </aside>
                {/* Main Content */}
                <main className="flex-1 p-4 md:p-6">
                    <div className="flex items-center justify-between">
                        <h1 className="text-2xl font-bold">Tablero</h1>
                        <div className="flex items-center gap-2">
                            <Button variant="outline" size="sm">
                                Hoy
                            </Button>
                            <Button variant="outline" size="icon">
                                <Plus className="h-4 w-4" />
                            </Button>
                            <Button variant="outline" size="icon">
                                <Calendar className="h-4 w-4" />
                            </Button>
                        </div>
                    </div>
                    <div className="mt-8">
                        <h2 className="text-lg font-semibold">Hoy</h2>
                        <p className="text-sm text-gray-500">20 de noviembre</p>
                        <div className="mt-4 grid gap-4">
                            <Card>
                                <CardContent className="flex items-center gap-4 p-4">
                                    <div className="flex-1">
                                        <div className="font-medium">ALGORITMOS Y ESTRUCTURAS DE DATOS</div>
                                        <div className="text-sm text-gray-500">Laboratorio 2.01 - 2024-2</div>
                                    </div>
                                    <div className="flex items-center gap-2">
                                        <Clock className="h-4 w-4 text-gray-500" />
                                        <span className="text-sm">9:00</span>
                                    </div>
                                    <Button size="sm">Ver detalles</Button>
                                </CardContent>
                            </Card>
                            <Card>
                                <CardContent className="flex items-center gap-4 p-4">
                                    <div className="flex-1">
                                        <div className="font-medium">DESARROLLO BASADO EN PLATAFORMAS</div>
                                        <div className="text-sm text-gray-500">Actividad en clase S14B</div>
                                    </div>
                                    <div className="flex items-center gap-2">
                                        <Clock className="h-4 w-4 text-gray-500" />
                                        <span className="text-sm">15:00</span>
                                    </div>
                                    <Button size="sm">Ver detalles</Button>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                    <div className="mt-8">
                        <h2 className="text-lg font-semibold">Próximas actividades</h2>
                        <div className="mt-4 grid gap-4">
                            <Card>
                                <CardContent className="flex items-center gap-4 p-4">
                                    <div className="flex-1">
                                        <div className="font-medium">EMPRESA Y CONSUMIDOR</div>
                                        <div className="text-sm text-gray-500">Dinámica - GRUPAL N°9</div>
                                    </div>
                                    <div className="flex items-center gap-2">
                                        <Calendar className="h-4 w-4 text-gray-500" />
                                        <span className="text-sm">21 nov</span>
                                    </div>
                                    <Button size="sm">Ver detalles</Button>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    )
}
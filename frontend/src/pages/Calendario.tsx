"use client"

import * as React from "react"
import Link from "next/link"
import Image from "next/image"
import { addDays, format } from "date-fns"
import { es } from "date-fns/locale"
import { Bell, Calendar as CalendarIcon, ChevronLeft, ChevronRight, Menu, Home, BookOpen, Users2, Inbox, LogOut } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { ScrollArea } from "@/components/ui/scroll-area"

export default function CalendarPage() {
    const [date, setDate] = React.useState<Date>(new Date())
    const [open, setOpen] = React.useState(false)

    // Ejemplo de eventos (en una aplicación real, estos vendrían de una API o base de datos)
    const events = [
        { id: 1, title: "Entrega de proyecto final", course: "Desarrollo Basado en Plataformas", date: addDays(new Date(), 2) },
        { id: 2, title: "Examen parcial", course: "Algoritmos y Estructuras de Datos", date: addDays(new Date(), 5) },
        { id: 3, title: "Presentación grupal", course: "Empresa y Consumidor", date: addDays(new Date(), 7) },
    ]

    return (
        <div className="min-h-screen bg-white">
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
                                    <Image
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
                                            <span className="text-xs text-gray-500">Alumno</span>
                                        </div>
                                    </div>
                                    <nav className="flex flex-col gap-2">
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            href="#"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Home className="h-5 w-5" />
                                            Inicio
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            href="#"
                                            onClick={() => setOpen(false)}
                                        >
                                            <BookOpen className="h-5 w-5" />
                                            Cursos
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg bg-gray-100 px-4 py-2 text-gray-900"
                                            href="#"
                                            onClick={() => setOpen(false)}
                                        >
                                            <CalendarIcon className="h-5 w-5" />
                                            Calendario
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            href="#"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Users2 className="h-5 w-5" />
                                            Grupos
                                        </Link>
                                        <Link
                                            className="flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            href="#"
                                            onClick={() => setOpen(false)}
                                        >
                                            <Inbox className="h-5 w-5" />
                                            Bandeja de entrada
                                        </Link>
                                        <Link
                                            className="mt-auto flex items-center gap-2 rounded-lg px-4 py-2 text-gray-700 hover:bg-gray-100"
                                            href="#"
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
                        <h1 className="text-lg font-semibold">Calendario</h1>
                    </div>
                    <Button variant="ghost" size="icon" className="shrink-0">
                        <Bell className="h-5 w-5" />
                        <span className="sr-only">Notificaciones</span>
                    </Button>
                </div>
            </header>
            <main className="container mx-auto p-4">
                <div className="grid gap-4 md:grid-cols-2">
                    <Card>
                        <CardHeader>
                            <CardTitle>Calendario</CardTitle>
                            <CardDescription>Visualiza y gestiona tus eventos</CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="flex items-center justify-between space-x-2">
                                <Popover>
                                    <PopoverTrigger asChild>
                                        <Button
                                            variant={"outline"}
                                            className="w-[240px] justify-start text-left font-normal"
                                        >
                                            <CalendarIcon className="mr-2 h-4 w-4" />
                                            {format(date, "MMMM yyyy", { locale: es })}
                                        </Button>
                                    </PopoverTrigger>
                                    <PopoverContent className="w-auto p-0" align="start">
                                        <Calendar
                                            mode="single"
                                            selected={date}
                                            onSelect={(newDate) => newDate && setDate(newDate)}
                                            initialFocus
                                        />
                                    </PopoverContent>
                                </Popover>
                                <div className="flex items-center space-x-2">
                                    <Button
                                        variant="outline"
                                        size="icon"
                                        onClick={() => setDate(addDays(date, -30))}
                                    >
                                        <ChevronLeft className="h-4 w-4" />
                                    </Button>
                                    <Button
                                        variant="outline"
                                        size="icon"
                                        onClick={() => setDate(addDays(date, 30))}
                                    >
                                        <ChevronRight className="h-4 w-4" />
                                    </Button>
                                </div>
                            </div>
                            <div className="mt-4">
                                <Calendar
                                    mode="single"
                                    selected={date}
                                    onSelect={(newDate) => newDate && setDate(newDate)}
                                    className="rounded-md border"
                                />
                            </div>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Próximos eventos</CardTitle>
                            <CardDescription>Tus actividades programadas</CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="space-y-4">
                                {events.map((event) => (
                                    <div key={event.id} className="flex items-center space-x-4">
                                        <div className="flex-shrink-0">
                                            <CalendarIcon className="h-5 w-5 text-gray-500" />
                                        </div>
                                        <div className="flex-1 min-w-0">
                                            <p className="text-sm font-medium text-gray-900 truncate">
                                                {event.title}
                                            </p>
                                            <p className="text-sm text-gray-500 truncate">
                                                {event.course}
                                            </p>
                                        </div>
                                        <div className="flex-shrink-0">
                                            <p className="text-sm text-gray-500">
                                                {format(event.date, "d 'de' MMMM", { locale: es })}
                                            </p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </CardContent>
                    </Card>
                </div>
            </main>
        </div>
    )
}
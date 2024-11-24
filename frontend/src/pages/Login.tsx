'use client'

import { Card, CardContent } from '../components/ui/card'
import React from 'react'

export default function LoginPage() {
    return (
        <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-[#19BFDF] to-[#221E1F]">
            <Card className="w-full max-w-md bg-white shadow-lg">
                <CardContent className="flex flex-col items-center p-8">
                    <img src="assets/utec-logo.svg?height=100&width=100" alt="Logo" className="mb-4" />
                    <h1 className="text-2xl font-bold mb-2">UTEC++</h1>

                </CardContent>
            </Card>
        </div>
    )
}


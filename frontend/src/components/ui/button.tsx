import React from "react";

interface ButtonProps {
    variant?: "ghost" | "outline";
    size?: "sm" | "icon";
    className?: string;
    children: React.ReactNode;
}

export const Button: React.FC<ButtonProps> = ({ variant = "outline", size = "sm", className = "", children }) => {
    const baseClass = "px-4 py-2 rounded-md font-medium focus:outline-none focus:ring";
    const variants = {
        ghost: "bg-transparent hover:bg-gray-200 text-gray-700",
        outline: "border border-gray-300 text-gray-700 hover:bg-gray-100",
    };
    const sizes = {
        sm: "text-sm",
        icon: "p-2",
    };
    return (
        <button
            className={`${baseClass} ${variants[variant]} ${sizes[size]} ${className}`}
        >
            {children}
        </button>
    );
};

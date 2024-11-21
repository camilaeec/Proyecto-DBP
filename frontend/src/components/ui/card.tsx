import React from "react";

interface CardProps {
    children: React.ReactNode;
    className?: string;
}

export const Card: React.FC<CardProps> = ({ children, className = "" }) => {
    return (
        <div className={`rounded-lg shadow-sm bg-white ${className}`}>
            {children}
        </div>
    );
};

export const CardContent: React.FC<CardProps> = ({ children, className = "" }) => {
    return <div className={`p-4 ${className}`}>{children}</div>;
};
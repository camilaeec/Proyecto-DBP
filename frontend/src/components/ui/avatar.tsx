import React from "react";

interface AvatarProps {
    src?: string;
    alt?: string;
    fallback?: string;
}

export const Avatar: React.FC<AvatarProps> = ({ src, alt, fallback = "?" }) => {
    return (
        <div className="relative h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center overflow-hidden">
            {src ? (
                <img src={src} alt={alt} className="h-full w-full object-cover" />
            ) : (
                <span className="text-gray-500">{fallback}</span>
            )}
        </div>
    );
};

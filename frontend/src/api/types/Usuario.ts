export interface Usuario {
    idUsuario: number;
    nombreUsuario: string;
    email: string;
    password: string;
    rol: 'Alumno' | 'Profesor' | 'Asesor';
}
